
WKS_FILE ?= "sdimage-unipi.wks"

require conf/machine/include/arm/armv8a/tune-cortexa72.inc
include conf/machine/include/rpi-base.inc
include conf/machine/unipi-edge-scr.inc

MACHINEOVERRIDES =. "raspberrypi4:unipi_edge:"

#MACHINE_FEATURES += "pci"
MACHINE_EXTRA_RRECOMMENDS += "\
    kernel-module-unipi-id \
    kernel-module-unipi-rfkill \
    kernel-module-unipi-mfds \
    unipi-modbus-tools \
    unipi-firmware-tools \
    unipi-os-configurator \
"
MACHINE_EXTRA_RRECOMMENDS += "${@bb.utils.contains('RPI_USE_U_BOOT', '1', 'u-boot-default-script', '', d)}"


# rpi-base.inc expects no RTC, but this machine has one
MACHINE_FEATURES_BACKFILL_CONSIDERED:remove = "rtc"

## clear some defaults from MACHINE_FEATURES
## this removes packagegroup-base-wifi... from RDEPENDS:packagegroup-base
## but not from RDEPENDS:packagegroup-base-extended
MACHINE_FEATURES:remove = "alsa"

## disable installing 3g,wifi,nfc,zeroconf if not in MACHINE_FEATURES
## or explicitly specified in IMAGE_INSTALL
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-3g"
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-wifi"
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-bluetooth"
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-nfc"
RDEPENDS:packagegroup-base:remove = "packagegroup-base-zeroconf"
RDEPENDS:packagegroup-core-base-utils:remove = "inetutils"

RPI_KERNEL_DEVICETREE = "broadcom/bcm2711-rpi-cm4.dtb"
BOOTIMG_DEVICETREE_OVERLAYS ?= "\
    overlays/vc4-kms-v3d-pi4.dtbo \
    overlays/unipi_uboot_tpm.dtbo \
    overlays/unipi_uboot.dtbo \
"
RPI_KERNEL_DEVICETREE_OVERLAYS = "\
    ${BOOTIMG_DEVICETREE_OVERLAYS} \
    overlays/unipi_e410.dtbo \
    overlays/unipi_e411.dtbo \
    overlays/unipi_e412.dtbo \
    overlays/unipi_e413.dtbo \
    overlays/unipi_e414.dtbo \
    overlays/antenna1.dtbo \
    overlays/disable_wifi.dtbo \
    overlays/disable_bt.dtbo \
"
BOOTIMG_DEVICETREE = "${RPI_KERNEL_DEVICETREE} ${BOOTIMG_DEVICETREE_OVERLAYS}"

# install dtb and dtbo into directories (broadcom, overlays)
KERNEL_DTBVENDORED = "${@bb.utils.contains('RPI_USE_U_BOOT', '1', '1', '0', d)}"

SDIMG_KERNELIMAGE ?= " \
        ${@bb.utils.contains('RPI_USE_U_BOOT', '1', \
                'u-boot.bin', \
                'kernel8.img', d)} \
        "

# Avoid re-generating fstab when ima is enabled.
WIC_CREATE_EXTRA_ARGS:append = " --no-fstab-update"

# disable serial console in Linux
#SERIAL_CONSOLES = "115200;ttyAMA1"
SERIAL_CONSOLES = ""

PREFERRED_VERSION_linux-raspberrypi ?= "6.12.%"

PREFERRED_PROVIDER_u-boot-default-script ?= "unipi-u-boot-scr"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
EXTRA_IMAGEDEPENDS += "virtual/bootloader"

UBOOT_MACHINE ?= "unipi_edge_config"
#KMACHINE = "unipi_edge"

VC4DTBO ?= "vc4-kms-v3d"


RPI_EXTRA_IMAGE_BOOT_FILES = " \
        ${@bb.utils.contains('RPI_USE_U_BOOT', '1', \
                'u-boot.bin', \
                '${KERNEL_IMAGETYPE};${SDIMG_KERNELIMAGE}', d)} \
        "

KERNEL_IMAGETYPE_UBOOT ?= "Image.gz"
KERNEL_IMAGETYPE_DIRECT ?= "Image.gz"

KERNEL_BOOTCMD ?= "booti"

#ARMSTUB ?= "armstub8-gic.bin"

############# Rules for building BOOTIMG requiredby RPI SECURE BOOT
# simple config.txt for bootimage
EXTRA_IMAGEDEPENDS += "bootimg-config bootimg"
do_image_wic[depends] += "bootimg-config:do_deploy bootimg:do_deploy"

# setting of files NOT installed to bootimage
FILTER_BOOTFILES ?= "bootcode.bin start4cd.elf fixup4cd.dat \
                     fixup_cd.dat fixup.dat fixup_db.dat fixup_x.dat \
                     start_cd.elf start_db.elf start.elf start_x.elf"

# list of files installed to bootimage
IMAGE_BOOTIMG_FILES = "${BOOTFILES_DIR_NAME}/* \
                       ${@make_dtb_boot_files(d)} \
                       ${RPI_EXTRA_IMAGE_BOOT_FILES} \
                      "

# list of files installed to native boot partition
IMAGE_BOOT_FILES = "${@bb.utils.contains('RPI_USE_U_BOOT', '1', \
                      'boot.img config.txt', \
                      '${IMAGE_BOOTIMG_FILES}', d)}"
