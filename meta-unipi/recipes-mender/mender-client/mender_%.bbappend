FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://mender-inventory-cpuinfo \
                   file://mender-device-identity \
"

# add dependency on libubootenv-bin if mender-uboot feature not set, but uboot is installed
RDEPENDS:mender-update:append = "${@bb.utils.contains('RPI_USE_U_BOOT', '1', ' libubootenv-bin', '', d)}"

# add inventory scripts for cpuinfo
do_install:append() {
    install -m 755 ${WORKDIR}/mender-inventory-cpuinfo ${D}${datadir}/mender/inventory/mender-inventory-cpuinfo
    if [ "${XMENDER_UNIPI_IDENTITY}" = "1" ]; then
        install -m 755 ${WORKDIR}/mender-device-identity ${D}${datadir}/mender/identity/mender-device-identity
    fi
}

FILES:mender-update += "${datadir}/mender/inventory/mender-inventory-cpuinfo"

# Remove dependency on GPL v3 program parted used in script mender-resize-data-part.sh.
# Replace parted with sfdisk and partx
RDEPENDS:mender-update:append:mender-growfs-data:mender-systemd = " util-linux-sfdisk util-linux-partx"
RDEPENDS:mender-update:remove = "parted"

require mender-tpm2.inc
