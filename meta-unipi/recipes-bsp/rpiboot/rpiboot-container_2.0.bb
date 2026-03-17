SUMMARY = "Boot image for Raspberry Pi secure boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^rpi$"

inherit rpiboot-container

BOOTIMAGE_RPI_EXTRA_DEPENDS ?= ""

DEPENDS += "rpiboot-config ${BOOTIMAGE_RPI_EXTRA_DEPENDS} rpi-config"

do_deploy[depends] += " \
    rpi-bootfiles:do_deploy \
    virtual/kernel:do_deploy \
    ${@bb.utils.contains('RPI_USE_U_BOOT', '1', 'u-boot:do_deploy', '', d)} \
"

# setting of files NOT installed to bootimage
FILTER_BOOTFILES ?= "bootcode.bin start4cd.elf fixup4cd.dat \
                     fixup_cd.dat fixup.dat fixup_db.dat fixup_x.dat \
                     start_cd.elf start_db.elf start.elf start_x.elf"

PACKAGE_ARCH = "${MACHINE_ARCH}"
