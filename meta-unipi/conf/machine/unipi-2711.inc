
WKS_FILE ?= "sdimage-unipi.wks"

require conf/machine/include/arm/armv8a/tune-cortexa72.inc
include conf/machine/include/rpi-base.inc
include conf/machine/unipi-2711-scr.inc

#MACHINE_FEATURES += "pci"
MACHINE_EXTRA_RRECOMMENDS += "\
    kernel-module-unipi-id \
    kernel-module-unipi-rfkill \
    unipi-os-configurator-switchboot \
    unipi-os-configurator-base \
"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "${@bb.utils.contains('RPI_USE_U_BOOT', '1', 'u-boot-default-script', '', d)}"


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
SERIAL_CONSOLES = ""

PREFERRED_VERSION_linux-raspberrypi ?= "6.12.%"

PREFERRED_PROVIDER_u-boot-default-script ?= "unipi-u-boot-scr"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
EXTRA_IMAGEDEPENDS += "virtual/bootloader"

# setup second (rootfs) partition as active instead of first FAT
IMAGE_CLASSES += "${@bb.utils.contains('RPI_USE_U_BOOT', '1', 'activate_part', '', d)}"

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

############# Rules for building RPIBOOT image required by RPi Secure boot
# RPI_USE_RPIBOOT = "1"
# RPI_USE_SIGNEDBOOT = "1"

EXTRA_IMAGEDEPENDS += "${@bb.utils.contains('RPI_USE_RPIBOOT', '1', 'rpiboot-container', '', d)}"
EXTRA_IMAGEDEPENDS += "${@bb.utils.contains('RPI_USE_SIGNEDBOOT', '1', 'rpiboot-sign', '', d)}"

# list of files installed to bootimage
RPIBOOT_IMAGE_FILES = "${BOOTFILES_DIR_NAME}/* \
                       ${@make_dtb_boot_files(d)} \
                       ${RPI_EXTRA_IMAGE_BOOT_FILES} \
                      "

# list of files installed to native boot partition
IMAGE_BOOT_FILES = "${@bb.utils.contains('RPI_USE_SIGNEDBOOT', '1', \
                        'boot.img config.txt boot.sig', \
                      bb.utils.contains('RPI_USE_RPIBOOT', '1', \
                        'boot.img config.txt', \
                        '${RPIBOOT_IMAGE_FILES}', d), d)}"

