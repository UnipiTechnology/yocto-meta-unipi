
# Fix rules which set undefined groups
do_install:append () {
}

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/etc.armhf/udev/rules.d/99-com.rules ${D}${sysconfdir}/udev/rules.d/
    sed -E 's/GROUP="((input)|(i2c)|(spi)|(gpio))"/GROUP="root"/' -i ${D}${sysconfdir}/udev/rules.d/99-com.rules
}
