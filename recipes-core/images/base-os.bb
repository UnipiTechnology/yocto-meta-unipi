SUMMARY = "compact image for Unipi PLC"
DESCRIPTION = "unipi.technology Base-os"
RECIPE_MAINTAINER = "Miroslav Ondra <ondra@unipi.technology>"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM ?= "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

IMAGE_FSTYPES = "ext4 wic"
IMAGE_OVERHEAD_FACTOR = "1.1"

IMAGE_FEATURES:append = " package-management"
IMAGE_FEATURES:append = " allow-root-login"
IMAGE_FEATURES:append = " allow-empty-password"
IMAGE_FEATURES:append = " empty-root-password"
IMAGE_FEATURES:append = " ssh-server-openssh"

IMAGE_FEATURES:remove = "splash"
#IMAGE_FEATURES:append = " post-install-logging"
#IMAGE_FEATURES:append = " x11-base"

IMAGE_INSTALL:append = " packagegroup-core-base-utils"
IMAGE_INSTALL:append = " packagegroup-security-tpm2"
IMAGE_INSTALL:append = " mbpoll"
IMAGE_INSTALL:append = " os-release"
IMAGE_INSTALL:append = " procps"
IMAGE_INSTALL:append = " file"
IMAGE_INSTALL:append = " mc"
IMAGE_INSTALL:append = " u-boot-tools-mkimage"
IMAGE_INSTALL:remove = "dhcpcd"
IMAGE_INSTALL:remove = "avahi"
IMAGE_INSTALL:remove = "ofono"

#IMAGE_INSTALL:append = " zile"
#IMAGE_INSTALL:append = " cpufrequtils"
# IMAGE_INSTALL:append = " openssl-bin"
#IMAGE_INSTALL:append = " rpidistro-ffmpeg"
#IMAGE_INSTALL:append =  " packagegroup-core-x11"
#IMAGE_INSTALL:append =  " packagegroup-xfce-base"
#IMAGE_INSTALL:append =  " mpv"

#IMAGE_INSTALL:append = " thcp"
#IMAGE_INSTALL:append = " imgui"

#inherit core-image features_check extrausers
inherit core-image extrausers

# mkpasswd -m sha256crypt <your-password>
# password: ppp
PASSWD = "\$5\$2qQtEpyiwk33Lj5/\$KK0mV7X4Mzt15EAo56iymdLUtL9Bbv0HWe8hpUZdhm1"
EXTRA_USERS_PARAMS = "\
    usermod -p '${PASSWD}' root; \
"

#REQUIRED_DISTRO_FEATURES = "cpufrequtils"
