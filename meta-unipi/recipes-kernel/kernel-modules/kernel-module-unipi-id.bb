
require kernel-module-unipi.inc

# For backwards compatibility
PROVIDES += "kernel-module-unipi-id"
RPROVIDES:${PN} = "kernel-module-unipi-id"

S = "${WORKDIR}/git/modules/unipi-id"
