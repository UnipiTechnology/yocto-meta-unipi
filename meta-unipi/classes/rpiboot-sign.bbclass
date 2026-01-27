#
# Sign boot image for Raspberry Pi 4 secure boot
#

INHIBIT_DEFAULT_DEPS = "1"
inherit deploy nopackages

def change_ext(variable, ext, d):
    filename = d.getVar(variable)
    import os.path
    return os.path.splitext(filename)[0] + ext

BOOTIMAGE_NAME ?= "boot.img"
SIGN_FILENAME = "${@change_ext('BOOTIMAGE_NAME', '.sig', d)}"
BOOT_IMG_CERTIFICATE_PEM ?= "${THISDIR}/files/non-production-demo.key.pem"

# Nothing to build, just install
do_configure[noexec] = "1"
do_compile[noexec] = "1"

DEPENDS += "openssl-native xxd-native"
do_install[depends] = " \
    openssl-native:do_populate_sysroot \
    xxd-native:do_populate_sysroot \
"
do_deploy[recrdeps] = "do_build"


do_install() {
    if [ -z "${BOOT_IMG_CERTIFICATE_PEM}" ]; then
        bbfatal "BOOT_IMG_CERTIFICATE_PEM is not set. Please specify the path to the .pem file."
    fi
    bootimage_path=${DEPLOY_DIR_IMAGE}/${BOOTIMAGE_NAME}
    sign_path=${B}/${SIGN_FILENAME}

    if [ -r "$bootimage_path" ]; then
        # Generate a signature file containing the sha256 hash of boot.img
        sha256sum "$bootimage_path" | cut -f 1 -d\  > "$sign_path"
        # next is the time of signing 
        printf 'ts: %s\n' "$(date -u +%s)" >> "$sign_path"

        # Generate RSA signature of the boot.img using openssl and hash it using sha256
        # append at the end of the sig file

        sig=$(openssl dgst -sha256 -sign "${BOOT_IMG_CERTIFICATE_PEM}" "$bootimage_path" | xxd -c 4096 -p )
        printf 'rsa2048: %s\n' "${sig}" >> "$sign_path"
    else
        bbwarn "$bootimage_path not found, skipping signature generation."
    fi
}

do_deploy() {
    if [ -f "${B}/${SIGN_FILENAME}" ]; then
        install -D -m 0644 "${B}/${SIGN_FILENAME}" "${DEPLOYDIR}/${SIGN_FILENAME}"
    else
        bbwarn "Signature file not found, skipping installation."
    fi
}

addtask deploy after do_install before do_build
