# Copyright 2024 (C) Unipi Technology, spol. s r.o.
SUMMARY = "Kernel modules for Unipi PLCs"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://github.com/UniPiTechnology/unipi-kernel-modules.git;protocol=https;branch=main"
SRCREV = "fbe5c69827cc0e7f68e029a4d09ddfb29bc46a87"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

