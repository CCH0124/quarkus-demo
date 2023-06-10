```bash
$ kubectl  -nvault exec vault-0 -it /bin/sh
$ vault login
Token (will be hidden):
Success! You are now authenticated. The token information displayed below
is already stored in the token helper. You do NOT need to run "vault login"
again. Future Vault requests will automatically use this token.

Key                  Value
---                  -----
token                hvs.eo6vvYhb06KqlySbYjPsJUeI
token_accessor       u7hMuGpQ2Wy3qGELwZuIDFku
token_duration       ∞
token_renewable      false
token_policies       ["root"]
identity_policies    []
policies             ["root"]
```

## 建立 ROOT CA
```bash
$ vault secrets enable -path=vault_root_ca pki # 新增一個 PKI 的路徑
Success! Enabled the pki secrets engine at: vault_root_ca/
$ vault secrets tune  -max-lease-ttl=87600h vault_root_ca #  調整 secrets engine configuration，將 vault_root_ca 的 max-lease-ttl 調整為 10 years (87600h)
Success! Tuned the secrets engine at: vault_root_ca/
~$ vault write -field=certificate vault_root_ca/root/generate/internal max_path_length=1 key_usage='' ext_key_usage='' common_name="Naruto Root CA" ou="Naruto PKI" organization="Naruto Corp" country="TW" ttl=
87600h key_bits=2048 > root-ca.crt
```


```bash
/pki/command$ openssl x509 -in root-ca.crt -text -noout
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            24:6a:ec:e0:aa:19:5a:3f:f7:3b:43:09:ff:cc:f4:1b:c7:fd:45:12
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C = TW, O = Naruto Corp, OU = Naruto PKI, CN = Naruto Root CA
        Validity
            Not Before: Jun  3 15:48:27 2023 GMT
            Not After : May 31 15:48:57 2033 GMT
        Subject: C = TW, O = Naruto Corp, OU = Naruto PKI, CN = Naruto Root CA
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:c5:72:9a:0b:73:c1:8a:3f:f2:34:2f:31:b9:b5:
                    9c:1f:04:b4:9d:22:c2:77:a0:3a:f2:c7:00:a5:cf:
                    50:88:54:d1:90:40:64:d7:c2:b6:dc:56:80:89:b7:
                    49:56:5b:1f:57:2c:09:bc:55:ba:da:ea:0b:01:45:
                    ed:ae:08:41:ca:d3:cc:de:5b:a5:fd:e6:98:5a:9b:
                    e9:23:8e:81:27:39:8b:61:f0:42:82:91:a7:3f:03:
                    04:42:64:48:dd:d1:13:df:21:18:d3:d9:e4:02:ac:
                    80:03:68:46:ed:64:7b:61:9c:db:b5:d2:29:9a:57:
                    c8:0e:0f:bf:e6:b2:03:f1:b7:a6:2b:c6:c5:f5:ba:
                    cb:3f:c0:ea:da:67:ad:7e:67:03:4c:e4:29:ff:0b:
                    d5:9c:7c:b7:84:ab:3d:22:96:ea:40:7f:ec:77:f6:
                    80:bc:e9:0a:0a:fa:1c:8e:d8:a1:fc:e4:a4:86:d7:
                    6c:45:d7:7e:79:30:80:57:0f:5d:fa:5e:bf:58:07:
                    91:2a:6a:d8:3c:4e:53:c4:d6:df:28:44:27:af:8b:
                    89:11:be:df:f3:6b:b2:71:64:ed:37:b3:b7:3b:b6:
                    20:18:db:1f:ac:18:3f:bb:c0:16:75:4a:e4:62:a4:
                    f7:57:48:23:84:17:66:37:02:b0:a6:54:22:b5:9a:
                    54:6f
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Key Usage: critical
                Certificate Sign, CRL Sign
            X509v3 Basic Constraints: critical
                CA:TRUE, pathlen:1
            X509v3 Subject Key Identifier: 
                64:45:95:E8:F5:FA:34:89:69:73:B0:50:67:D6:F7:8E:33:1D:6B:89
            X509v3 Authority Key Identifier: 
                64:45:95:E8:F5:FA:34:89:69:73:B0:50:67:D6:F7:8E:33:1D:6B:89
    Signature Algorithm: sha256WithRSAEncryption
    Signature Value:
        40:fb:84:3f:11:b3:d8:cd:58:2b:6c:da:26:ca:8a:8e:92:74:
        db:d0:c7:78:18:ff:8d:92:40:24:4a:14:b5:59:50:f3:e0:78:
        8f:67:74:ae:cd:6b:a6:4f:e0:ba:d0:21:96:58:1c:5f:3f:62:
        ba:f9:76:0a:0b:16:39:28:a0:10:73:d3:f8:3d:62:4a:d2:87:
        81:bd:9b:03:37:81:e2:de:6e:91:b3:50:b7:26:0d:5b:db:10:
        cf:c2:d5:07:98:62:ea:d3:f4:89:de:e3:a5:11:48:db:cb:62:
        85:2b:08:a3:f3:83:f8:76:f7:52:c3:46:eb:ae:44:12:7d:db:
        3e:cf:68:87:e2:ee:d6:80:3f:ad:76:9a:12:e2:66:63:3d:e8:
        e6:e2:52:b3:a0:29:89:9e:5a:7c:16:0f:f4:19:69:ba:0b:34:
        9a:c0:b8:ec:6a:f9:55:67:a6:19:b1:dc:f5:05:26:06:2f:2f:
        5e:a6:97:51:5e:c4:8c:08:14:ff:86:b3:72:ac:3f:24:9a:67:
        39:3e:5f:35:6a:3e:d0:29:27:fa:e4:aa:93:95:da:07:f7:02:
        12:59:ad:9a:8d:1c:65:b2:b7:aa:18:2d:1d:20:41:27:d3:15:
        71:12:4b:f1:27:4b:0d:e2:1d:19:47:6d:e2:33:ca:b6:cd:9d:
        81:7b:dc:ea
```

## 建立 Intermediate CA

```bash
~ $ vault secrets enable -path=vault_intermediate_ca pki
Success! Enabled the pki secrets engine at: vault_intermediate_ca/
~ $ vault secrets tune -max-lease-ttl=43800h vault_intermediate_ca
Success! Tuned the secrets engine at: vault_intermediate_ca/
$ vault write -format=json vault_intermediate_ca/intermediate/generate/internal max_path_length=0 key_bits=2048 common_name="Naruto Issuing CA" ou="Naruto PKI" organization="Naruto Corp" country="TW" issuer_name="naruto-intermediate" | jq -r '.data.csr' > naruto_intermediate.csr
$ vault write -format=json vault_root_ca/root/sign-intermediate max_path_length=0 key_bits=2048 common_name="Naruto Issuing CA" ou="Naruto" organization="Naruto Corp" country="TW" csr=@naruto_intermediate.csr format=pem_bundle ttl="43800h" | jq -r '.data.certificate' > naruto_intermediate.cert.pem 

$ vault write vault_intermediate_ca/intermediate/set-signed certificate=@naruto_intermediate.cert.pem # 回寫憑證
WARNING! The following warnings were returned from Vault:

  * This mount hasn't configured any authority information access (AIA)
  fields; this may make it harder for systems to find missing certificates
  in the chain or to validate revocation status of certificates. Consider
  updating /config/urls or the newly generated issuer with this information.

Key                 Value
---                 -----
imported_issuers    [368576c2-a72c-d5f3-dde0-f5dd0fb533ea a01c8bf9-aea8-3201-704b-1496b01126d3]
imported_keys       <nil>
mapping             map[368576c2-a72c-d5f3-dde0-f5dd0fb533ea:898602e1-769d-7101-50f0-b2be703bfdd6 a01c8bf9-aea8-3201-704b-1496b01126d3:]
```

在使用 CSR 向 Root CA 簽發 Intermediate CA 時，如果希望是 CSR 裡面內容可以使用 `use_csr_values=true`。詳細資訊可參考[use_csr_values](https://developer.hashicorp.com/vault/api-docs/secret/pki#use_csr_values)

```bash
$ openssl x509 -in naruto_intermediate.cert.pem -text -noout
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            70:34:88:33:be:b2:16:e7:0a:78:19:e8:a7:15:05:ed:db:d2:44:17
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C = TW, O = Naruto Corp, OU = Naruto PKI, CN = Naruto Root CA
        Validity
            Not Before: Jun  3 16:24:51 2023 GMT
            Not After : Jun  1 16:25:21 2028 GMT
        Subject: C = TW, O = Naruto Corp, OU = Naruto, CN = Naruto Issuing CA
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:c7:e6:8f:ca:66:d9:13:23:4c:5f:f8:a3:e9:a2:
                    fe:d1:7e:b7:e4:ef:37:c5:ec:25:da:53:f5:f6:97:
                    74:8c:bf:87:b9:22:c1:9c:93:87:0c:23:9c:7a:88:
                    eb:c2:29:6c:92:58:5b:9b:da:10:03:c9:37:3a:e1:
                    64:3e:9b:9a:eb:e4:3f:6a:fb:8f:99:51:36:e1:0c:
                    15:8a:93:74:90:c6:7f:33:45:70:e2:c3:aa:13:cc:
                    5b:a8:45:b7:f3:f8:45:4e:40:99:58:68:aa:34:f2:
                    f0:f1:42:45:97:f6:26:3d:c2:f1:f6:6c:7e:72:9a:
                    62:a2:e3:4f:93:73:02:dd:1d:fe:dd:ad:08:96:ea:
                    74:08:59:bf:3c:f4:bd:19:20:d1:49:ed:04:fe:ad:
                    ba:99:b8:da:d3:be:a1:8c:8a:a5:15:a1:50:8f:e4:
                    c1:eb:6b:06:91:43:69:68:59:99:db:74:06:04:f8:
                    e4:e2:e4:9e:30:67:89:46:b5:d5:e3:49:d7:f1:8e:
                    52:0b:da:7a:72:f1:aa:26:4c:a3:9b:c2:a1:dc:0f:
                    50:32:73:b6:ab:9f:da:f6:c2:11:76:aa:b0:fb:5f:
                    0d:b1:38:22:e4:7d:76:5b:bf:ac:e2:8b:95:7c:8b:
                    ba:9f:42:68:73:dd:1f:b7:a4:d6:b1:bb:12:8b:b5:
                    fb:f7
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Key Usage: critical
                Certificate Sign, CRL Sign
            X509v3 Basic Constraints: critical
                CA:TRUE, pathlen:0
            X509v3 Subject Key Identifier: 
                DB:35:2A:A6:8B:B8:D0:83:5E:CB:F3:1F:79:62:F9:A2:75:12:A2:84
            X509v3 Authority Key Identifier: 
                64:45:95:E8:F5:FA:34:89:69:73:B0:50:67:D6:F7:8E:33:1D:6B:89
    Signature Algorithm: sha256WithRSAEncryption
    Signature Value:
        65:41:3e:91:ff:a0:10:62:a7:0c:91:b9:0b:c7:fb:1f:b3:80:
        7d:73:d8:7d:a0:9c:36:3b:7e:2e:28:52:f0:c0:6b:95:2a:45:
        b3:c6:8a:b4:0c:65:20:f2:53:44:3c:4b:0c:b9:ae:d5:82:d5:
        5b:5e:0a:b3:06:25:75:51:bf:0f:77:ca:46:16:ac:21:1a:13:
        cb:18:fb:26:c2:c4:b1:cc:9c:30:4e:3e:a7:05:31:f5:a6:1c:
        4e:30:c1:a8:ac:aa:c5:ac:cb:43:0d:de:30:04:c1:4b:b4:7d:
        e0:ba:d6:e0:27:07:47:f3:9f:05:21:79:bb:08:e4:e0:00:f1:
        c0:7f:7a:9a:59:16:b5:a9:7c:c5:a6:26:38:03:54:95:77:68:
        69:91:3e:a8:de:32:c2:56:fb:a6:6b:73:ff:e0:68:5a:4a:fd:
        4b:e0:55:59:1d:b1:85:7a:b5:b7:12:56:c4:ba:56:ef:43:15:
        de:c9:27:7c:62:28:0c:2a:a3:4e:13:ad:b0:8a:20:b4:b5:c4:
        f6:f1:94:2b:d5:cd:12:d0:77:ea:76:aa:cd:6a:53:7e:06:55:
        bf:b7:bf:ef:fe:64:7b:a1:ac:51:a6:6b:7d:dc:c0:02:49:07:
        60:60:de:d3:93:45:a0:b5:ae:c0:e0:ec:cd:c5:eb:ed:88:e5:
        34:76:3b:09
```

## 建立 Role 並簽發給用戶端憑證

```bash
$ vault write vault_intermediate_ca/roles/Issuer allow_any_name=True server_flag=True client_flag=True allow_subdomains=false ou="Konohagakure Center"  organization="Naruto Corp" country="TW" key_usage="DigitalSignature, KeyEncipherment" use_csr_common_name=True ext_key_usage="" ext_key_usage_oids="" ttl="365d" max_ttl="365d"
Key                                   Value
---                                   -----
allow_any_name                        true
allow_bare_domains                    false
allow_glob_domains                    false
allow_ip_sans                         true
allow_localhost                       true
allow_subdomains                      false
allow_token_displayname               false
allow_wildcard_certificates           true
allowed_domains                       []
allowed_domains_template              false
allowed_other_sans                    []
allowed_serial_numbers                []
allowed_uri_sans                      []
allowed_uri_sans_template             false
allowed_user_ids                      []
basic_constraints_valid_for_non_ca    false
client_flag                           true
cn_validations                        [email hostname]
code_signing_flag                     false
country                               [TW]
email_protection_flag                 false
enforce_hostnames                     true
ext_key_usage                         []
ext_key_usage_oids                    []
generate_lease                        false
issuer_ref                            default
key_bits                              2048
key_type                              rsa
key_usage                             [DigitalSignature KeyEncipherment]
locality                              []
max_ttl                               8760h
no_store                              false
not_after                             n/a
not_before_duration                   30s
organization                          [Naruto Corp]
ou                                    [Konohagakure Center]
policy_identifiers                    []
postal_code                           []
province                              []
require_cn                            true
server_flag                           true
signature_bits                        256
street_address                        []
ttl                                   8760h
use_csr_common_name                   true
use_csr_sans                          true
use_pss                               false
```

其中 `vault_intermediate_ca/roles/Issuer` 中的 Issuer 為 role name。

簽發
```bash
$ vault write vault_intermediate_ca/issue/Issuer common_name="madara" ttl="365d"
Key                 Value
---                 -----
ca_chain            [-----BEGIN CERTIFICATE-----
MIIDlTCCAn2gAwIBAgIUcDSIM76yFucKeBnopxUF7dvSRBcwDQYJKoZIhvcNAQEL
BQAwUTELMAkGA1UEBhMCVFcxFDASBgNVBAoTC05hcnV0byBDb3JwMRMwEQYDVQQL
EwpOYXJ1dG8gUEtJMRcwFQYDVQQDEw5OYXJ1dG8gUm9vdCBDQTAeFw0yMzA2MDMx
NjI0NTFaFw0yODA2MDExNjI1MjFaMFAxCzAJBgNVBAYTAlRXMRQwEgYDVQQKEwtO
YXJ1dG8gQ29ycDEPMA0GA1UECxMGTmFydXRvMRowGAYDVQQDExFOYXJ1dG8gSXNz
dWluZyBDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMfmj8pm2RMj
TF/4o+mi/tF+t+TvN8XsJdpT9faXdIy/h7kiwZyThwwjnHqI68IpbJJYW5vaEAPJ
NzrhZD6bmuvkP2r7j5lRNuEMFYqTdJDGfzNFcOLDqhPMW6hFt/P4RU5AmVhoqjTy
8PFCRZf2Jj3C8fZsfnKaYqLjT5NzAt0d/t2tCJbqdAhZvzz0vRkg0UntBP6tupm4
2tO+oYyKpRWhUI/kwetrBpFDaWhZmdt0BgT45OLknjBniUa11eNJ1/GOUgvaenLx
qiZMo5vCodwPUDJztquf2vbCEXaqsPtfDbE4IuR9dlu/rOKLlXyLup9CaHPdH7ek
1rG7Eou1+/cCAwEAAaNmMGQwDgYDVR0PAQH/BAQDAgEGMBIGA1UdEwEB/wQIMAYB
Af8CAQAwHQYDVR0OBBYEFNs1KqaLuNCDXsvzH3li+aJ1EqKEMB8GA1UdIwQYMBaA
FGRFlej1+jSJaXOwUGfW944zHWuJMA0GCSqGSIb3DQEBCwUAA4IBAQBlQT6R/6AQ
YqcMkbkLx/sfs4B9c9h9oJw2O34uKFLwwGuVKkWzxoq0DGUg8lNEPEsMua7VgtVb
XgqzBiV1Ub8Pd8pGFqwhGhPLGPsmwsSxzJwwTj6nBTH1phxOMMGorKrFrMtDDd4w
BMFLtH3gutbgJwdH858FIXm7COTgAPHAf3qaWRa1qXzFpiY4A1SVd2hpkT6o3jLC
Vvuma3P/4GhaSv1L4FVZHbGFerW3ElbEulbvQxXeySd8YigMKqNOE62wiiC0tcT2
8ZQr1c0S0HfqdqrNalN+BlW/t7/v/mR7oaxRpmt93MACSQdgYN7Tk0Wgta7A4OzN
xevtiOU0djsJ
-----END CERTIFICATE----- -----BEGIN CERTIFICATE-----
MIIDljCCAn6gAwIBAgIUJGrs4KoZWj/3O0MJ/8z0G8f9RRIwDQYJKoZIhvcNAQEL
BQAwUTELMAkGA1UEBhMCVFcxFDASBgNVBAoTC05hcnV0byBDb3JwMRMwEQYDVQQL
EwpOYXJ1dG8gUEtJMRcwFQYDVQQDEw5OYXJ1dG8gUm9vdCBDQTAeFw0yMzA2MDMx
NTQ4MjdaFw0zMzA1MzExNTQ4NTdaMFExCzAJBgNVBAYTAlRXMRQwEgYDVQQKEwtO
YXJ1dG8gQ29ycDETMBEGA1UECxMKTmFydXRvIFBLSTEXMBUGA1UEAxMOTmFydXRv
IFJvb3QgQ0EwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDFcpoLc8GK
P/I0LzG5tZwfBLSdIsJ3oDryxwClz1CIVNGQQGTXwrbcVoCJt0lWWx9XLAm8Vbra
6gsBRe2uCEHK08zeW6X95pham+kjjoEnOYth8EKCkac/AwRCZEjd0RPfIRjT2eQC
rIADaEbtZHthnNu10imaV8gOD7/msgPxt6YrxsX1uss/wOraZ61+ZwNM5Cn/C9Wc
fLeEqz0ilupAf+x39oC86QoK+hyO2KH85KSG12xF1355MIBXD136Xr9YB5Eqatg8
TlPE1t8oRCevi4kRvt/za7JxZO03s7c7tiAY2x+sGD+7wBZ1SuRipPdXSCOEF2Y3
ArCmVCK1mlRvAgMBAAGjZjBkMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAG
AQH/AgEBMB0GA1UdDgQWBBRkRZXo9fo0iWlzsFBn1veOMx1riTAfBgNVHSMEGDAW
gBRkRZXo9fo0iWlzsFBn1veOMx1riTANBgkqhkiG9w0BAQsFAAOCAQEAQPuEPxGz
2M1YK2zaJsqKjpJ029DHeBj/jZJAJEoUtVlQ8+B4j2d0rs1rpk/gutAhllgcXz9i
uvl2CgsWOSigEHPT+D1iStKHgb2bAzeB4t5ukbNQtyYNW9sQz8LVB5hi6tP0id7j
pRFI28tihSsIo/OD+Hb3UsNG665EEn3bPs9oh+Lu1oA/rXaaEuJmYz3o5uJSs6Ap
iZ5afBYP9Blpugs0msC47Gr5VWemGbHc9QUmBi8vXqaXUV7EjAgU/4azcqw/JJpn
OT5fNWo+0Ckn+uSqk5XaB/cCElmtmo0cZbK3qhgtHSBBJ9MVcRJL8SdLDeIdGUdt
4jPKts2dgXvc6g==
-----END CERTIFICATE-----]
certificate         -----BEGIN CERTIFICATE-----
MIIDtjCCAp6gAwIBAgIUDbqU8bmfZ6kGw1if1mOXGS756xQwDQYJKoZIhvcNAQEL
BQAwUDELMAkGA1UEBhMCVFcxFDASBgNVBAoTC05hcnV0byBDb3JwMQ8wDQYDVQQL
EwZOYXJ1dG8xGjAYBgNVBAMTEU5hcnV0byBJc3N1aW5nIENBMB4XDTIzMDYwMzE2
NTI1NVoXDTI0MDYwMjE2NTMyNVowUjELMAkGA1UEBhMCVFcxFDASBgNVBAoTC05h
cnV0byBDb3JwMRwwGgYDVQQLExNLb25vaGFnYWt1cmUgQ2VudGVyMQ8wDQYDVQQD
EwZtYWRhcmEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMqP3qhfYX
VRZXfxlzSUe7PrSCPNvT4Jis5gwlYsaaJZP7C+ye4ovEGyuqQ1yt6CeOBhEWjfur
U1qGwmrLdpKbgqTgFfUiZVbxi75PW+LhS1K27J0DowpclEFnSmb0kv5YsWpGKnFb
oYk2phBvZb8hjcK93vYbc5kMEzNPqjwHgpqYJk8D42oZhR0U+lyGByTGR6LvC6lT
t+odiqN9QxZL0jdktOGMSp3Yo+BDo0szGQWKfR9PuQ0Z/EOh+v/vJzxDvRhh/P0Y
Qo31M/NW5cz+0cTxILct7a8H73L68eILNnE1Qi7LDB3TH2XdX9l/Nd+U+Ei2Jweu
I6WyKbkIYezdAgMBAAGjgYUwgYIwDgYDVR0PAQH/BAQDAgWgMB0GA1UdJQQWMBQG
CCsGAQUFBwMBBggrBgEFBQcDAjAdBgNVHQ4EFgQUgGZ9dybBFrADN2FpcsgwnndH
w94wHwYDVR0jBBgwFoAU2zUqpou40INey/MfeWL5onUSooQwEQYDVR0RBAowCIIG
bWFkYXJhMA0GCSqGSIb3DQEBCwUAA4IBAQAOPwEIcNq6pPLndz3oZrcNDnmWO3UK
qaO0m9Au3wvY0uRHaNfwUCjugvjIOZzWDOUUs6cPy7uIGlU5ByMw7SIPUWhrsAAg
dgwRBAEPG3ewFU54KBlEHbmk6zzcxcG3Ybf7P0hTSVZEbJkKKYNtJtr8VuaHn8Nr
DiIqm09yP9mdr0UOZ6q56ifAI/pCMP0Yf8qst0uFYu0G+OWKbLnOfhVVkckpP3Oz
//bGIm4N/+ZW1tTdsj7TUQsDxseM0/RWjEpLIxIB5rCeUjK0jvM2+8Ub1/o+wyhS
/CZ/5c8VNpg6JX9olkAQB4FYMt3Am7GxA1twbQg/x8uJ3vk+wR+fg1d2
-----END CERTIFICATE-----
expiration          1717347205
issuing_ca          -----BEGIN CERTIFICATE-----
MIIDlTCCAn2gAwIBAgIUcDSIM76yFucKeBnopxUF7dvSRBcwDQYJKoZIhvcNAQEL
BQAwUTELMAkGA1UEBhMCVFcxFDASBgNVBAoTC05hcnV0byBDb3JwMRMwEQYDVQQL
EwpOYXJ1dG8gUEtJMRcwFQYDVQQDEw5OYXJ1dG8gUm9vdCBDQTAeFw0yMzA2MDMx
NjI0NTFaFw0yODA2MDExNjI1MjFaMFAxCzAJBgNVBAYTAlRXMRQwEgYDVQQKEwtO
YXJ1dG8gQ29ycDEPMA0GA1UECxMGTmFydXRvMRowGAYDVQQDExFOYXJ1dG8gSXNz
dWluZyBDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMfmj8pm2RMj
TF/4o+mi/tF+t+TvN8XsJdpT9faXdIy/h7kiwZyThwwjnHqI68IpbJJYW5vaEAPJ
NzrhZD6bmuvkP2r7j5lRNuEMFYqTdJDGfzNFcOLDqhPMW6hFt/P4RU5AmVhoqjTy
8PFCRZf2Jj3C8fZsfnKaYqLjT5NzAt0d/t2tCJbqdAhZvzz0vRkg0UntBP6tupm4
2tO+oYyKpRWhUI/kwetrBpFDaWhZmdt0BgT45OLknjBniUa11eNJ1/GOUgvaenLx
qiZMo5vCodwPUDJztquf2vbCEXaqsPtfDbE4IuR9dlu/rOKLlXyLup9CaHPdH7ek
1rG7Eou1+/cCAwEAAaNmMGQwDgYDVR0PAQH/BAQDAgEGMBIGA1UdEwEB/wQIMAYB
Af8CAQAwHQYDVR0OBBYEFNs1KqaLuNCDXsvzH3li+aJ1EqKEMB8GA1UdIwQYMBaA
FGRFlej1+jSJaXOwUGfW944zHWuJMA0GCSqGSIb3DQEBCwUAA4IBAQBlQT6R/6AQ
YqcMkbkLx/sfs4B9c9h9oJw2O34uKFLwwGuVKkWzxoq0DGUg8lNEPEsMua7VgtVb
XgqzBiV1Ub8Pd8pGFqwhGhPLGPsmwsSxzJwwTj6nBTH1phxOMMGorKrFrMtDDd4w
BMFLtH3gutbgJwdH858FIXm7COTgAPHAf3qaWRa1qXzFpiY4A1SVd2hpkT6o3jLC
Vvuma3P/4GhaSv1L4FVZHbGFerW3ElbEulbvQxXeySd8YigMKqNOE62wiiC0tcT2
8ZQr1c0S0HfqdqrNalN+BlW/t7/v/mR7oaxRpmt93MACSQdgYN7Tk0Wgta7A4OzN
xevtiOU0djsJ
-----END CERTIFICATE-----
private_key         -----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQEAzKj96oX2F1UWV38Zc0lHuz60gjzb0+CYrOYMJWLGmiWT+wvs
nuKLxBsrqkNcregnjgYRFo37q1NahsJqy3aSm4Kk4BX1ImVW8Yu+T1vi4UtStuyd
A6MKXJRBZ0pm9JL+WLFqRipxW6GJNqYQb2W/IY3Cvd72G3OZDBMzT6o8B4KamCZP
A+NqGYUdFPpchgckxkei7wupU7fqHYqjfUMWS9I3ZLThjEqd2KPgQ6NLMxkFin0f
T7kNGfxDofr/7yc8Q70YYfz9GEKN9TPzVuXM/tHE8SC3Le2vB+9y+vHiCzZxNUIu
ywwd0x9l3V/ZfzXflPhIticHriOlsim5CGHs3QIDAQABAoIBAF+0eyELyhv5HzyF
ZCpkT1YBqbnpqcb4FY1s+qsU+Nimz/+rANkACeoH5rB1VcbBdJXWwnDwrorcrM2t
5vEEnuR4AJAF1J9kEndcKP/FcWB3r9N47KgBu1u1vKwHwAOJJcXmGVN8j4wRCzBy
Eed2Bh4zw6i0thP6R8y8wdb0PaQomfSwZ/zBVwTltVtAs8iV40XbGGRamcH72EYf
f28xs0WOwAZVtlIjy01nQXKUyjXm04vC6lAV48tddl9uiKBCfx/u6voD+5BGA/L/
VCLdLDb8GhJRRe0rRQad0DAgehtSfBHOZ7fbxJQC5QvuLOxR4YDrYSl3b0aNsX/F
gRo49WECgYEA4RovK358GwUymYqbhcXu4nDOfqhDqfKLWeiM6xJLUPosmOoyk431
xseB08oVfJcYrdNKoO2lFZgqQ3Yi09dUWxC/k9VgXd+UJev/p0bIvo/MuKzvq8sd
GpIgZ4xjI5b877jyuSJi+//NT0C+eY3FIIfymLlDPClBEOrJffqyDQkCgYEA6MB+
1sMqDG1nJwVe3zn4b0axf6Gp94oXXn9aYKlwsAQjOJPN/MSzq4uRvYnh5noctvQl
hUQhUPNr5L+nn4Ca5Lh7bzUarM0yLzFDimJqMobGBc5M28kub0XUVXd030if2qUg
4Lxv2+tmIIuUZSjj9pWhkullmOYdy+OknoaD6jUCgYB0X04NhUlxyqEbIb4/TVGv
fTQz5Jd+j6adJ41apc2MIpnVwBW/lL+AMXob3Lh4/cBDdR16zwDuhgkrcTKWJBc3
MRRpwAEe5sw3QqebegukAMGMo9MQFGSKbQsVnU1Pg+ploE7TiUR1FQy2MTmHq9RC
eiXf8L8ipg0+SrM6TtWnyQKBgGSI4UC6xCriBrBgLX6Wd6z5CCptFhGKYFW9BLjD
95zP7La/gTYxcLokaytHp3/6NvT/uEU8DHf+7Re9gRXwYvdwiECc24zQhWDFErtv
jxeMB3Tyi1hUKe5+Zx0ToDPiFspGkVEBk/+WSmK8Z7jaVL1qHIRstCm7HBu5lJ20
1L4RAoGAYxYz7/8QCsUn4ovMCJaMLYKzvPWwLV7RBMbJWm/OeqQvKsCPd1tL1bOf
XI+JfDBecPtBjYDZm0P9Qgy5YkJCpcWJS8kLhT+8gk4bW5BuO5AGykanCTUX5R3j
3IRUoeZgMMq7E8eFtHBKPD2s9GsTxj61dR9YpDGTdR+stb7dt48=
-----END RSA PRIVATE KEY-----
private_key_type    rsa
serial_number       0d:ba:94:f1:b9:9f:67:a9:06:c3:58:9f:d6:63:97:19:2e:f9:eb:14
```

解憑證

```bash
$ openssl x509 -in client.crt -text -noout
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            0d:ba:94:f1:b9:9f:67:a9:06:c3:58:9f:d6:63:97:19:2e:f9:eb:14
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C = TW, O = Naruto Corp, OU = Naruto, CN = Naruto Issuing CA
        Validity
            Not Before: Jun  3 16:52:55 2023 GMT
            Not After : Jun  2 16:53:25 2024 GMT
        Subject: C = TW, O = Naruto Corp, OU = Konohagakure Center, CN = madara
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:cc:a8:fd:ea:85:f6:17:55:16:57:7f:19:73:49:
                    47:bb:3e:b4:82:3c:db:d3:e0:98:ac:e6:0c:25:62:
                    c6:9a:25:93:fb:0b:ec:9e:e2:8b:c4:1b:2b:aa:43:
                    5c:ad:e8:27:8e:06:11:16:8d:fb:ab:53:5a:86:c2:
                    6a:cb:76:92:9b:82:a4:e0:15:f5:22:65:56:f1:8b:
                    be:4f:5b:e2:e1:4b:52:b6:ec:9d:03:a3:0a:5c:94:
                    41:67:4a:66:f4:92:fe:58:b1:6a:46:2a:71:5b:a1:
                    89:36:a6:10:6f:65:bf:21:8d:c2:bd:de:f6:1b:73:
                    99:0c:13:33:4f:aa:3c:07:82:9a:98:26:4f:03:e3:
                    6a:19:85:1d:14:fa:5c:86:07:24:c6:47:a2:ef:0b:
                    a9:53:b7:ea:1d:8a:a3:7d:43:16:4b:d2:37:64:b4:
                    e1:8c:4a:9d:d8:a3:e0:43:a3:4b:33:19:05:8a:7d:
                    1f:4f:b9:0d:19:fc:43:a1:fa:ff:ef:27:3c:43:bd:
                    18:61:fc:fd:18:42:8d:f5:33:f3:56:e5:cc:fe:d1:
                    c4:f1:20:b7:2d:ed:af:07:ef:72:fa:f1:e2:0b:36:
                    71:35:42:2e:cb:0c:1d:d3:1f:65:dd:5f:d9:7f:35:
                    df:94:f8:48:b6:27:07:ae:23:a5:b2:29:b9:08:61:
                    ec:dd
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Key Usage: critical
                Digital Signature, Key Encipherment
            X509v3 Extended Key Usage: 
                TLS Web Server Authentication, TLS Web Client Authentication
            X509v3 Subject Key Identifier: 
                80:66:7D:77:26:C1:16:B0:03:37:61:69:72:C8:30:9E:77:47:C3:DE
            X509v3 Authority Key Identifier: 
                DB:35:2A:A6:8B:B8:D0:83:5E:CB:F3:1F:79:62:F9:A2:75:12:A2:84
            X509v3 Subject Alternative Name: 
                DNS:madara
    Signature Algorithm: sha256WithRSAEncryption
    Signature Value:
        0e:3f:01:08:70:da:ba:a4:f2:e7:77:3d:e8:66:b7:0d:0e:79:
        96:3b:75:0a:a9:a3:b4:9b:d0:2e:df:0b:d8:d2:e4:47:68:d7:
        f0:50:28:ee:82:f8:c8:39:9c:d6:0c:e5:14:b3:a7:0f:cb:bb:
        88:1a:55:39:07:23:30:ed:22:0f:51:68:6b:b0:00:20:76:0c:
        11:04:01:0f:1b:77:b0:15:4e:78:28:19:44:1d:b9:a4:eb:3c:
        dc:c5:c1:b7:61:b7:fb:3f:48:53:49:56:44:6c:99:0a:29:83:
        6d:26:da:fc:56:e6:87:9f:c3:6b:0e:22:2a:9b:4f:72:3f:d9:
        9d:af:45:0e:67:aa:b9:ea:27:c0:23:fa:42:30:fd:18:7f:ca:
        ac:b7:4b:85:62:ed:06:f8:e5:8a:6c:b9:ce:7e:15:55:91:c9:
        29:3f:73:b3:ff:f6:c6:22:6e:0d:ff:e6:56:d6:d4:dd:b2:3e:
        d3:51:0b:03:c6:c7:8c:d3:f4:56:8c:4a:4b:23:12:01:e6:b0:
        9e:52:32:b4:8e:f3:36:fb:c5:1b:d7:fa:3e:c3:28:52:fc:26:
        7f:e5:cf:15:36:98:3a:25:7f:68:96:40:10:07:81:58:32:dd:
        c0:9b:b1:b1:03:5b:70:6d:08:3f:c7:cb:89:de:f9:3e:c1:1f:
        9f:83:57:76
```