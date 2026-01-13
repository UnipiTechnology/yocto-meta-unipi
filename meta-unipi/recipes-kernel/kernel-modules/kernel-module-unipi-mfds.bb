# Copyright 2024 (C) Unipi Technology, spol. s r.o.
SUMMARY = "Kernel modules for Unipi PLCs"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# For backwards compatibility
PROVIDES += "kernel-module-unipi-mfds"
#RPROVIDES:${PN} = "kernel-module-unipi-mfd"

SRCBRANCH = "main-trixie"
SRC_URI = "git://git.unipi.technology/UniPi/unipi-kernel-modules;protocol=https;branch=${SRCBRANCH}"
SRCREV = "f754b62d68f6a5c1983dc606b152ed6922ae022e"

S = "${WORKDIR}/git/modules/unipi-mfd"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

#RDEPENDS:${PN} += "kernel-module-unipi-unipi"
#RDEPENDS:${PN} += "kernel-module-unipi-modbus"
# kernel-module-unipi-leds\
# kernel-module-unipi-gpio\
# kernel-module-unipi-iio\
# kernel-module-unipi-iio-legacy\
# kernel-module-unipi-iogroup-bus\
# kernel-module-unipi-uart"
#COMPATIBLE_MACHINE = "(zulu)"
