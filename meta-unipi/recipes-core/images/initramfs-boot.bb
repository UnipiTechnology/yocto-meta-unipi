SUMMARY = "Create package from boot initramfs"
DESCRIPTION = "Create package from generated initramfs. PAckage can be installed into main rootfs"
LICENSE = "MIT"

INITRAMFS_IMAGE_NAME ?= "${INITRAMFS_IMAGE}"
INITRAMFS_IMAGE_FILE ?= "${INITRAMFS_IMAGE_NAME}-${MACHINE}.cpio.gz"
INITRAMFS_FILE = "${PN}-${MACHINE}"

DEPENDS = "${INITRAMFS_IMAGE_NAME}"
ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dev = ""
ALLOW_EMPTY:${PN}-dbg = ""

FILES:${PN} += "boot/${INITRAMFS_FILE}"

# Nothing to build, just install
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/boot
    install -m 644 ${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE_FILE} ${D}/boot/${INITRAMFS_FILE}
}

do_install[depends] += "${INITRAMFS_IMAGE_NAME}:do_image_complete"

PACKAGE_ARCH = "${MACHINE_ARCH}"
