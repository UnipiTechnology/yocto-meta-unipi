DESCRIPTION = "Commented config.txt file for the Raspberry Pi. \
               The Raspberry Pi config.txt file is read by the GPU before \
               the ARM core is initialised. It can be used to set various \
               system configuration parameters."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

COMPATIBLE_MACHINE = "unipi_edge"

SRC_URI = "file://config.txt"

S = "${WORKDIR}"

INHIBIT_DEFAULT_DEPS = "1"

VC4GRAPHICS="${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "1", "0", d)}"
VC4DTBO ?= "vc4-kms-v3d"

ENABLE_UART ??= ""

inherit deploy nopackages

do_deploy() {
    install -d ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}
    CONFIG=${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt

    cp ${S}/config.txt $CONFIG

    if [ "${RPI_USE_U_BOOT}" = "1" ]; then
        sed -i '/^.*kernel=.*$/ c\kernel=u-boot.bin' $CONFIG
    else
        sed -i '/kernel=/ c\#kernel=' $CONFIG
    fi

    # Set overclocking options
    if [ -n "${ARM_FREQ}" ]; then
        sed -i '/#arm_freq=/ c\arm_freq=${ARM_FREQ}' $CONFIG
    fi
    if [ -n "${GPU_FREQ}" ]; then
        sed -i '/#gpu_freq=/ c\gpu_freq=${GPU_FREQ}' $CONFIG
    fi
    if [ -n "${CORE_FREQ}" ]; then
        sed -i '/#core_freq=/ c\core_freq=${CORE_FREQ}' $CONFIG
    fi

    # Set HDMI and composite video options
    if [ -n "${HDMI_FORCE_HOTPLUG}" ]; then
        sed -i '/#hdmi_force_hotplug=/ c\hdmi_force_hotplug=${HDMI_FORCE_HOTPLUG}' $CONFIG
    fi
    if [ -n "${DISPLAY_ROTATE}" ]; then
        sed -i '/#display_rotate=/ c\display_rotate=${DISPLAY_ROTATE}' $CONFIG
    fi

    if [ "${VC4GRAPHICS}" = "1" ]; then
        sed -i '/#dtoverlay=vc4-kms-v3d-pi4/ c\dtoverlay=vc4-kms-v3d-pi4' $CONFIG
    fi

    # Waveshare "C" 1024x600 7" Rev2.1 IPS capacitive touch (http://www.waveshare.com/7inch-HDMI-LCD-C.htm)
    if [ "${WAVESHARE_1024X600_C_2_1}" = "1" ]; then
        echo "# Waveshare \"C\" 1024x600 7\" Rev2.1 IPS capacitive touch screen" >> $CONFIG
        echo "max_usb_current=1" >> $CONFIG
        echo "hdmi_group=2" >> $CONFIG
        echo "hdmi_mode=87" >> $CONFIG
        echo "hdmi_cvt 1024 600 60 6 0 0 0" >> $CONFIG
        echo "hdmi_drive=1" >> $CONFIG
    fi

    # Append extra config if the user has provided any
    printf "${RPI_EXTRA_CONFIG}\n" >> $CONFIG

    # Reduce config.txt file size to avoid corruption and
    # to boot successfully Raspberry Pi 5. The issue has
    # been reported to related projects:
    # https://github.com/raspberrypi/firmware/issues/1848
    # https://github.com/Evilpaul/RPi-config/issues/9
    sed -i '/^##/d' $CONFIG
}

do_deploy:append() {
    if grep -q -E '^.{80}.$' ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/config.txt; then
        bbwarn "config.txt contains lines longer than 80 characters, this is not supported"
    fi
}

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
