
SRC_URI:append = " file://ssl-tpm2.cnf \
                   file://tpm2-override.conf \
                   file://agent-key.conf \
"

do_install:append() {
    if echo "${XMENDER_AUTH_PRIVATE_KEY}" | grep -q 'handle:'; then
        install -m 644 -D ${WORKDIR}/ssl-tpm2.cnf ${D}${sysconfdir}/ssl/ssl-tpm2.cnf
        install -m 644 -D ${WORKDIR}/tpm2-override.conf ${D}${sysconfdir}/systemd/system/mender-authd.service.d/tpm2-override.conf
    fi
    if echo "${XMENDER_AUTH_PRIVATE_KEY}" | grep -q 'r2k:'; then
        install -m 644 -D ${WORKDIR}/agent-key.conf ${D}${sysconfdir}/systemd/system/mender-authd.service.d/agent-key.conf
    fi
}

python do_post_prepare_mender_conf() {
    import json
    pk = d.getVar('XMENDER_AUTH_PRIVATE_KEY')
    if pk is None or pk == "":
        return
    if pk.startswith("r2k:"):
        pk = pk[len('r2k:'):]
        pk = pk if pk else "mender-agent.pem"
        pk = f"/run/mender/{pk}"

    src_conf = os.path.join(d.getVar("B"), "persistent_mender.conf")
    node = None
    if os.path.exists(src_conf):
        fd = open(src_conf)
        c_config = json.load(fd)
        fd.close()
        node = c_config.get("Security")
    if node is None:
        src_conf = os.path.join(d.getVar("B"), "transient_mender.conf")
        if os.path.exists(src_conf):
            fd = open(src_conf)
            c_config = json.load(fd)
            fd.close()
            node = c_config.get("Security", {})
        else:
            c_config = {}
            node = {}
    if type(node) is not dict:
        bb.warn("Configuration key 'Security', found in mender.conf, is not dictionary. Ovewriting it.")
        node = {}
    if node.get("AuthPrivateKey") is not None and node.get["AuthPrivateKey"] != pk:
        bb.warn("Configuration key 'AuthPrivateKey', found in mender.conf, conflicts with '%s'. Choosing the latter." % (key))
    node["AuthPrivateKey"] = pk
    c_config["Security"] = node

    fd = open(src_conf, "w")
    json.dump(c_config, fd, indent=4, sort_keys=True)
    fd.close()
}

addtask do_post_prepare_mender_conf after do_prepare_mender_conf before do_install
do_post_prepare_mender_conf[vardeps] = "XMENDER_AUTH_PRIVATE_KEY"

FILES:mender-auth:append = "${@' ${sysconfdir}/ssl/ssl-tpm2.cnf \
                                 ${sysconfdir}/systemd/system/mender-authd.service.d/tpm2-override.conf' \
                               if str.startswith(d.getVar('XMENDER_AUTH_PRIVATE_KEY'), 'handle:') else ''}"
FILES:mender-auth:append = "${@' ${sysconfdir}/systemd/system/mender-authd.service.d/agent-key.conf' \
                               if d.getVar('XMENDER_AUTH_PRIVATE_KEY').startswith('r2k:') else ''}"

RDEPENDS:mender-auth:append = "${@bb.utils.contains('XMENDER_AUTH_PRIVATE_KEY','r2k:',' unipi-modbus-tools','',d)}"
