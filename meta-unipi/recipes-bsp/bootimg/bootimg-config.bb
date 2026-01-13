SUMMARY = "config.txt for Raspberry Pi secure boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^rpi$"

SRC_URI = "file://config.txt.in"
#SRC_URI[config.txt.in.sha256sum] = "8f05caab9c87d35c5539ad04dadf2082dc05fd89a25fa2cf9093a3d2a409da58"

INHIBIT_DEFAULT_DEPS = "1"
inherit deploy nopackages

do_configure() {
    :
}

do_compile() {
    cat "${WORKDIR}/config.txt.in" > ${B}/config.txt
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/config.txt ${DEPLOYDIR}
}

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
