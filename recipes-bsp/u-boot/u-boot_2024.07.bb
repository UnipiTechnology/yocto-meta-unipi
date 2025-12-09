
UBOOT_MACHINE := "${@'unipi_arm64_config' if d.getVar('UBOOT_MACHINE') == 'unipi_edge_config' else  d.getVar('UBOOT_MACHINE')}"

require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

SRCREV = "3f772959501c99fbe5aa0b22a36efe3478d1ae1c"
SRC_URI:append = " \
 file://0001-Change-order-of-discovering-partitions.patch \
 file://0002-Add-compatibility-with-unipi-rtc-unipi.patch \
 file://0003-Add-driver-for-i2c-bcm2835.patch \
 file://0004-Modify-command-date-to-read-date-into-variable.patch \
 file://0005-Add-support-for-unipi_eprom-and-altboot-button.patch \
 file://0006-Add-support-for-bootcount-in-ds1307-rtc-chip.patch \
 file://0007-Add-support-for-udma-ranges-as-override-of-dma-range.patch \
 file://0008-Remove-unknown-function-used-in-cmd-data.c.patch \
 file://0009-Add-support-for-RS485-console.patch \
 file://0010-Add-new-boot-methods-altboot-and-tryboot.patch \
 file://0011-Backport-fix-dhcp-boot-script.-Fix-flags-setting-unb.patch \
 file://0012-Prepare-board-files-for-bootstd-system.patch \
 file://0013-Add-default-config-for-Edge.patch \
 file://0014-Add-delay-2.5s-for-waiting-to-power-good-USB-port.patch \
 file://0015-Hotfix-loading-problem-files-from-btrfs.-Remove-unwa.patch \
 file://0016-Revert-hotfix-and-backport-fix-from-mainline.patch \
 file://0017-Set-console-to-ttyAMA1.patch \
 file://0018-Add-support-for-reading-MAC-addresses-from-eprom.patch \
 file://0019-Add-driver-for-TPM2-on-SPI.patch \
 file://0020-Set-calibration-value-from-eprom-to-RTC.patch \
 file://0021-Fix-rs485_tx_op-in-rpi.c.patch \
"

DEPENDS += "bc-native dtc-native python3-pyelftools-native"
COMPATIBLE_MACHINE = "unipi-edge"
RDEPENDS:${PN} += "u-boot-default-script"
