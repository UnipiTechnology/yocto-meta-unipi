include conf/machine/unipi-2711.inc

MACHINEOVERRIDES =. "raspberrypi4:unipi_edge:"

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
    overlays/unipi_e415.dtbo \
    overlays/antenna1.dtbo \
    overlays/disable_wifi.dtbo \
    overlays/disable_bt.dtbo \
"
BOOTIMG_DEVICETREE = "${RPI_KERNEL_DEVICETREE} ${BOOTIMG_DEVICETREE_OVERLAYS}"

UBOOT_MACHINE ?= "unipi-edge_config"
