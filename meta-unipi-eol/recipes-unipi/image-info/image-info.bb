SUMMARY = "Unipi EOL image boot confirm script"
DESCRIPTION = "Install temporary systemd unit for confirmation\
               of successfull boot of OS image.\
               Service collects some informations about running system \
               and send it to defined API endpoint.\
"

HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://image-info.service.in"

#inherit systemd
inherit kernel-arch

PACKAGES = "image-info"

UNIPI_EOL_IMAGENAME_CMD ?= "cat /etc/unipi_image_name||true"
UNIPI_EOL_IMAGEVERSION_CMD ?= "cat /etc/unipi_image_version||true"
UNIPI_EOL_POSTEXEC_CMD ?= "/bin/true"

UNIPI_EOL_DESTINATION = "${@'${OVERLAYFS_ETC_MOUNT_POINT}/overlay-etc/upper' if d.getVar('OVERLAYFS_ETC_MOUNT_POINT', False) else '/etc'}"

do_compile() {
    sed -e "s#@@UNIPI_EOL_IMAGENAME_CMD@@#${UNIPI_EOL_IMAGENAME_CMD}#" \
        -e "s#@@UNIPI_EOL_IMAGEVERSION_CMD@@#${UNIPI_EOL_IMAGEVERSION_CMD}#" \
        -e "s#@@UNIPI_EOL_POSTEXEC_CMD@@#${UNIPI_EOL_POSTEXEC_CMD}#" \
        "${WORKDIR}/image-info.service.in" > image-info.service
}

do_install() {
    pwd
    ls -1 *
    install -d ${D}${UNIPI_EOL_DESTINATION}/systemd/system/multi-user.target.wants
    install -m 0644 image-info.service ${D}${UNIPI_EOL_DESTINATION}/systemd/system/
    ln -s ${UNIPI_EOL_DESTINATION}/systemd/system/image-info.service \
          ${D}${UNIPI_EOL_DESTINATION}/systemd/system/multi-user.target.wants/image-info.service
    ${UNIPI_EOL_BUILD_INSTALL_CMD}
}

FILES:${PN} = "${UNIPI_EOL_DESTINATION}"

RDEPENDS:${PN} += " curl"
