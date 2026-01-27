SUMMARY = "Boot image for Raspberry Pi secure boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^rpi$"

#SRC_URI = "file://config.txt.in"
#SRC_URI[config.txt.in.sha256sum] = "8f05caab9c87d35c5539ad04dadf2082dc05fd89a25fa2cf9093a3d2a409da58"

INHIBIT_DEFAULT_DEPS = "1"
inherit deploy nopackages

DEPENDS = "dosfstools-native \
           mtools-native \
           rpi-bootfiles u-boot \
           linux-raspberrypi \
           rpiboot-config \
          "

do_configure() {
    :
}

do_compile() {
    :
}

python prepare_bootimg_source() {
    import re
    from glob import glob
    boot_files = d.getVar('RPIBOOT_IMAGE_FILES')
    kernel_dir = d.getVar('DEPLOY_DIR_IMAGE')
    filter_bootfiles = d.getVar('FILTER_BOOTFILES') or ''
    filter_files = filter_bootfiles.split()

    deploy_files = []
    for src_entry in re.findall(r'[\w;\-\./\*]+', boot_files):
        if ';' in src_entry:
            dst_entry = tuple(src_entry.split(';'))
            if not dst_entry[0] or not dst_entry[1]:
                raise Exception('Malformed boot file entry: %s' % src_entry)
        else:
            dst_entry = (src_entry, src_entry)

        #bb.debug(1, 'Destination entry: %s' % (dst_entry,))
        deploy_files.append(dst_entry)

    install_task = [];
    # allocate minimal number of directories
    d_count = 16
    f_count = 0
    f_sectors = 0
    for deploy_entry in deploy_files:
        src, dst = deploy_entry
        if '*' in src:
            # by default install files under their basename
            entry_name_fn = os.path.basename
            if dst != src:
                # unless a target name was given, then treat name
                # as a directory and append a basename
                entry_name_fn = lambda name: \
                                os.path.join(dst,
                                             os.path.basename(name))
            srcs = glob(os.path.join(kernel_dir, src))
            #bb.debug(1, 'Globbed sources: %s' % (','.join(srcs)))
            for entry in srcs:
                src = os.path.relpath(entry, kernel_dir)
                if os.path.basename(src) not in filter_files:
                    f_sectors += (os.stat(entry).st_size + 511)//512
                    f_count += 1
                    entry_dst_name = entry_name_fn(entry)
                    install_task.append((src, entry_dst_name))
                    bb.debug(1, 'Task: %s -> %s' % (src, entry_dst_name))
        else:
            f_sectors += (os.stat(os.path.join(kernel_dir, src)).st_size + 511)//512
            f_count += 1
            install_task.append((src, dst))
            bb.debug(1, 'Task: %s -> %s' % (src, dst))

    # Calculate the size required for the final image including the
    # data and filesystem overhead.
    # Sectors: 512 bytes
    #  Blocks: 1024 bytes
    #
    # FAT - 4 bytes per sector + 4 bytes for end_of_cluster list
    fat_bytes = (f_sectors * 4) + (d_count + f_count) * 4
    # there are 2 FATs
    fat_sectors = (fat_bytes+511)//512 * 2
    # estimate 2 sectors for directory - 32 entries in directory
    dir_sectors = d_count * 2
    # estimate blocks in kB
    blocks = (f_sectors + fat_sectors + dir_sectors)// 2 + 2
    bb.debug(1, 'Blocks: %d kB, Dir entries: %d, Files: %d bytes' % (blocks, f_count+d_count, f_sectors*512))
    d.setVar('BOOTIMAGE_BLOCKS', str(blocks))
    d.setVar('BOOTIMAGE_INSTALL', ' '.join(('%s:%s' % (s,d) for s,d in install_task)))
}

build_fat_img() {
    FATSOURCEDIR=${DEPLOY_DIR_IMAGE}/${BOOTFILES_DIR_NAME}
    FATIMG=${DEPLOYDIR}/boot.img
    BLOCKS=${BOOTIMAGE_BLOCKS}

    # mkdosfs will sometimes use FAT16 when it is not appropriate,
    # resulting in a boot failure from SYSLINUX. Use FAT32 for
    # images larger than 512MB, otherwise let mkdosfs decide.
    if [ $(expr $BLOCKS / 1024) -gt 512 ]; then
         FATSIZE="-F 32"
    fi

    # mkdosfs will fail if ${FATIMG} exists. Since we are creating an
    # new image, it is safe to delete any previous image.
    if [ -e ${FATIMG} ]; then
        rm ${FATIMG}
    fi

    mkdosfs ${FATSIZE} -n BOOTIMG -C ${FATIMG} ${BLOCKS}

    # Copy FATSOURCEDIR recursively into the image file directly
    #mcopy -i ${FATIMG} -s ${FATSOURCEDIR}/* ::/

    for entry in ${BOOTIMAGE_INSTALL} ; do
        # Split entry at optional ':' to enable file renaming for the destination
        if [ $(echo "$entry" | grep -c :) = "0" ] ; then
            DEPLOY_FILE="$entry"
            DEST_FILENAME="$entry"
        else
            DEPLOY_FILE="$(echo "$entry" | cut -f1 -d:)"
            DEST_FILENAME="$(echo "$entry" | cut -f2- -d:)"
        fi
        if [ $(dirname ${DEST_FILENAME}) != "." ]; then
            mmd -i ${FATIMG} ::$(dirname ${DEST_FILENAME}) || true
        fi
        mcopy -v -i ${FATIMG} -s ${DEPLOY_DIR_IMAGE}/${DEPLOY_FILE} ::${DEST_FILENAME} \
          || bbfatal "mcopy cannot copy ${DEPLOY_DIR_IMAGE}/${DEPLOY_FILE} into boot.img"
    done
}


python do_deploy() {
    bb.build.exec_func("prepare_bootimg_source", d)
    bb.build.exec_func("build_fat_img", d)
}

do_deploy[depends] += "dosfstools-native:do_populate_sysroot \
                       mtools-native:do_populate_sysroot \
                       rpi-bootfiles:do_deploy \
                       u-boot:do_deploy \
                       linux-raspberrypi:do_deploy"

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
