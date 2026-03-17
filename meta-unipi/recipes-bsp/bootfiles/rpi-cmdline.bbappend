## Don't use cmdline in case of using U-boot

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:unipi_edge = "unipi_edge"
COMPATIBLE_MACHINE:unipi_neuron = "unipi_neuron"

do_deploy() {
    install -d "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"
    if [ "${RPI_USE_U_BOOT}" != "1" ]; then
        install -m 0644 "${WORKDIR}/cmdline.txt" "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}"
    else
        install -m 0644 /dev/null "${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/cmdline.txt"
    fi
}
