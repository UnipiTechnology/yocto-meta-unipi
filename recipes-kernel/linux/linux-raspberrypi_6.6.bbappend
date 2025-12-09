
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "\
  file://defconfig\
  file://0001-Add-RTC_PERSISTENT_CLOCK_PARAMETER-to-kernel-source.patch\
  file://0002-Add-RxTx-led-on-amba-pl011.patch\
  file://0005-Add-driver-for-BCM2835-DMA-mux.patch\
  file://0006-Add-mctrl_gpio-support-to-amba-pl011-serial-driver.patch\
  file://0008-Fix-amba-pl011-serial-driver-remove-udelay-in-RS485.patch\
  file://0100-Add-Edge-overlays.patch\
"

# To enable local defconfig, we must deactivate  KBUILD_DEFCONFIG
KBUILD_DEFCONFIG:unipi-edge = ""

RDEPENDS:${KERNEL_PACKAGE_NAME}-base = "${KERNEL_PACKAGE_NAME}-devicetree"
