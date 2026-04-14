include conf/machine/unipi-2711.inc

MACHINEOVERRIDES =. "raspberrypi4:unipi_neuron:"

RPI_KERNEL_DEVICETREE = "broadcom/bcm2711-rpi-4-b.dtb"

BOOTIMG_DEVICETREE_OVERLAYS ?= "\
     overlays/vc4-kms-v3d-pi4.dtbo \
     overlays/unipi_uboot.dtbo \
"

RPI_KERNEL_DEVICETREE_OVERLAYS = "\
     ${BOOTIMG_DEVICETREE_OVERLAYS} \
     overlays/unipi_s103.dtbo \
     overlays/antenna1.dtbo \
     overlays/disable_wifi.dtbo \
     overlays/disable_bt.dtbo \
"
BOOTIMG_DEVICETREE = "${RPI_KERNEL_DEVICETREE} ${BOOTIMG_DEVICETREE_OVERLAYS}"

UBOOT_MACHINE ?= "unipi-edge_config"

MACHINE_FEATURES += "bluetooth"
MACHINE_FEATURES += "wifi"

MACHINE_EXTRA_RRECOMMENDS += "\
    kernel-module-unipi-mfds \
    unipi-modbus-tools \
    unipi-firmware-tools \
    linux-firmware-rpidistro-bcm43455 \
    bluez-firmware-rpidistro-bcm4345c0-hcd \
    linux-firmware-rpidistro-bcm43456 \
    bluez-firmware-rpidistro-bcm4345c5-hcd \
"
