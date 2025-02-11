DESCRIPTION = "Boot script for launching images with U-Boot distro boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "u-boot-mkimage-native make"

SRCBRANCH = "dev-bootcount"
SRCREV = "33d18234c9c43510711c21b8e96b82c44e01a9c1"
SRC_URI = "git://git.unipi.technology/UniPi/zulu-u-boot;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"
FILES:${PN} = "/etc/bootcmd.d"
RDEPENDS:${PN} += "make"

inherit deploy

#do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_configure(){
    sed -e 's/vmlinux/${KERNEL_IMAGETYPE}/' \
        -i '${S}/debian/bootcmd.d/src/05-defkernel.conf'
}

do_install () {
    mkdir -p ${D}${sysconfdir}/bootcmd.d
    cp -Rf -t ${D}${sysconfdir} "${S}/debian/bootcmd.d"
}

do_deploy() {
    cd "${D}${sysconfdir}/bootcmd.d"
    make force BOOTSCR="${DEPLOYDIR}/boot.scr-${MACHINE}-${PV}-${PR}"
    #install -Dm 0644 ${D}/boot.scr ${DEPLOYDIR}/boot.scr-${MACHINE}-${PV}-${PR}
    cd ${DEPLOYDIR}
    rm -f boot.scr-${MACHINE}
    ln -sf boot.scr-${MACHINE}-${PV}-${PR} boot.scr-${MACHINE}
}

addtask deploy after do_install before do_build

PROVIDES += "u-boot-default-script"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(zulu)"
