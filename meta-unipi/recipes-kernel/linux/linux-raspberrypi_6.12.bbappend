
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}_6.12:"
SRC_URI += "\
  file://defconfig\
  file://0001-Add-driver-for-BCM2835-DMA-mux.patch\
  file://0002-serial-amba-pl011-Fix-RTS-handling-in-RS485-mode.patch\
  file://0003-serial-amba-pl011-Add-mctrl_gpio.patch\
  file://0004-Import-unipi-ds.dtsi-into-cm4-dts.patch\
  file://unipi-ds.dtsi;subdir=git/arch/arm/boot/dts/broadcom\
  file://altboot-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://antenna1-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_e410-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_e411-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_e412-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_e413-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_e414-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_uboot-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://unipi_uboot_tpm-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://disable_wifi-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
  file://disable_bt-overlay.dts;subdir=git/arch/arm/boot/dts/overlays\
"

# To enable local defconfig, we must deactivate  KBUILD_DEFCONFIG
KBUILD_DEFCONFIG:unipi_edge = ""

RDEPENDS:${KERNEL_PACKAGE_NAME}-base = "${KERNEL_PACKAGE_NAME}-devicetree"
