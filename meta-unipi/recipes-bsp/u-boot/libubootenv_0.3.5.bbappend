FILESEXTRAPATHS:prepend := "${THISDIR}/libubootenv:"

SRC_URI:append = " file://fw_printenv \
                   file://fw_setenv \
"

do_install:append() {
    if [ "${RPI_USE_U_BOOT}" = "1" ]; then
        rm -f ${D}/usr/bin/fw_printenv
        rm -f ${D}/usr/bin/fw_setenv
        install -m 755 ${WORKDIR}/fw_printenv ${D}/usr/bin
        install -m 755 ${WORKDIR}/fw_setenv ${D}/usr/bin
    fi
}
