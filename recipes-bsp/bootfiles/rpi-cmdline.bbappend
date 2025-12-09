## Don't use cmdline in case of usin U-boot

COMPATIBLE_MACHINE = "unipi-edge"

do_deploy() {
    if [ "${RPI_USE_U_BOOT}" != "1" ]; then
        install -d "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"
        install -m 0644 "${WORKDIR}/cmdline.txt" "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"
    fi
}
