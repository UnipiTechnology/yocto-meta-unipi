SUMMARY = "Unipi Tools"
DESCRIPTION = "Modbus server and firmware flashing tool for internal units \
and external extensions connected to Unipi controller. \
"
HOMEPAGE = "https://git.unipi.technology/UniPi/unipi-tools/"
SECTION = "console/utils"

DEPENDS = "libmodbus (>=3.1.4) libmhash ${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd','',d)}"

SRC_URI = "git://git.unipi.technology/UniPi/unipi-tools;protocol=https;branch=master"

inherit autotools gettext pkgconfig ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', 'update-rc.d', d)}

S = "${WORKDIR}/git"
EXTRA_OEMAKE = " PROJECT_VERSION=${PV}"

# split into packages
PACKAGES =+ " unipi-firmware-tools unipi-modbus-tools"

FILES:unipi-firmware-tools = "\
               ${bindir}/fwspi \
               ${bindir}/fwserial \
               ${systemd_unitdir}/system/unipifwupdate.service \
"
FILES:unipi-modbus-tools = "\
               ${sbindir}/unipitcp \
               ${bindir}/r2k \
               ${systemd_unitdir}/system/unipitcp.service \
               ${sysconfdir}/default/unipitcp \
               ${sysconfdir}/init.d/unipitcp \
"

CONFFILES:unipi-modbus-tools += "${sysconfdir}/default/unipitcp"
SYSTEMD_SERVICE:${PN} = "unipitcp.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
#SYSTEMD_SERVICE:unipi-firmware-tools = "unipifwupdate.service"

INITSCRIPT_NAME:unipi-modbus-tools = "unipitcp"
INITSCRIPT_PARAMS:unipi-modbus-tools = "start 50 2 3 4 5 ."

BBCLASSEXTEND = "native"
