SUMMARY = "Boot image for Raspberry Pi secure boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^rpi$"

inherit rpiboot-sign

DEPENDS += "rpiboot-container"

do_install[depends] += "rpiboot-container:do_deploy"

PACKAGE_ARCH = "${MACHINE_ARCH}"
