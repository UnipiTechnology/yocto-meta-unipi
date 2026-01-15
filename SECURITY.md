# Security Policy

This document describes the security-related expectations and responsibilities for the Unipi Edge Yocto BSP.

---

## Scope

This BSP provides a technical foundation for building embedded Linux images for **Unipi Edge** devices.  
It does **not** represent a complete product security solution on its own.

Security features and policies must be evaluated and implemented in the context of the final product.

---

## Secure Boot

Support for Secure Boot depends on the underlying hardware and SoC capabilities.

This BSP:

- Is compatible with Secure Boot–enabled configurations where supported by the platform
- Does not enable Secure Boot by default
- Does not generate, manage, or distribute cryptographic keys

Key generation, ownership, storage, and lifecycle management are the responsibility of the integrator or product owner.

---

## Vulnerability Management

Integrators are responsible for:

- Tracking security advisories relevant to their product configuration
- Applying updates and patches as required
- Rebuilding and redeploying images when security fixes are introduced

---

## Reporting Security Issues

Security issues related to this BSP should be reported through official Unipi communication channels.

Please do not disclose security vulnerabilities publicly before coordinated resolution.

---

## Disclaimer

This BSP is provided **as-is**, without warranty of any kind.  
Security suitability must be evaluated as part of the overall system and product risk assessment.
