## Configuration

The on-premises installation has two major configuration points - (1) the location of the Structurizr data directory,
and (2) configuration of the on-premises installation itself via a file named `structurizr.properties` inside the Structurizr data directory.

### Structurizr data directory

The location of the Structurizr data directory can be configured in a number of ways:

- An environment variable named `STRUCTURIZR_DATA_DIRECTORY`
- A JVM system property named `structurizr.dataDirectory`

If unset, the Structurizr data directory location will default to `/usr/local/structurizr`.

### structurizr.properties

The following parameters can be set in a text file named `structurizr.properties` in your Structurizr data directory.
Changing these parameters requires a restart of the on-premises installation.

Values can either be static strings, or references to environment variables
(e.g. `structurizr.redis.password=${REDIS_PASSWORD}`).

| Name                               | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `structurizr.dslEditor`            | Whether the browser-based DSL editor is enabled: `true` or `false` (default).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `structurizr.url`                  | If you are running the on-premises installation behind a load balancer and/or reverse-proxy (e.g. SSL termination is being handled upstream), or the pages served by the on-premises installation don't look right (e.g. styles are not loading, images are oversized, etc), you will likely need to set this property to explicitly tell the on-premises installation the URL you are using to access it. This should be a full URL, such as `https://structurizr.example.com`.                                                                                                                                                                                                            |
| `structurizr.encryption`           | By default, workspace data is stored as plaintext on disk. Setting this property will enable server-side encryption. For better security (and to keep the encryption passphrase away from the encrypted files), you can specify this property as an environment variable (`STRUCTURIZR_ENCRYPTION`) or a JVM system property (`structurizr.encryption`), rather than putting this in the `structurizr.properties` file.
| `structurizr.authentication`       | The name of the authentication implementation to use: `file` (default), `ldap` (see [LDAP](04-authentication.md#ldap) for more details), or `saml` (see [SAML 2.0](04-authentication.md#saml-20) for more details).                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| `structurizr.session`              | The name of the HTTP session storage implementation to use: `local` (default) or `redis`. See [HTTP sessions](06-http-sessions.md) for more details.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| `structurizr.admin`                | By default, any authenticated user can create workspaces. If you would like to restrict who can create workspaces, set this property to a comma-separated list of usernames or roles that should have "admin" access.                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| `structurizr.data`                 | The name of the data storage implementation to use: `file` (default) or `aws-s3` (see [Amazon Web Services S3](06-data-storage#amazon-web-services-s3) for more details).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| `structurizr.search`               | The name of the search implementation to use: `lucene` (default), `none`, or `elasticsearch` (see [Elasticsearch](06-data-storage#elasticsearch) for more details).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| `structurizr.maxWorkspaceVersions` | The number of workspace versions to retain when using file-based data storage (default; `30`).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| `structurizr.safeMode`             | Whether HTML should be filtered from workspace content; `true` (default) or `false`.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |

### HTTPS

There are several options for running the on-premises installation over HTTPS, including:

- Configure HTTPS in the web/application server hosting the on-premises installation; for example [SSL/TLS Configuration How-To for Apache Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/ssl-howto.html).
- Configure HTTPS upstream; for example [Load Balancing Apache Tomcat Servers with NGINX Open Source and NGINX Plus](https://docs.nginx.com/nginx/deployment-guides/load-balance-third-party/apache-tomcat/), [Create an HTTPS listener for your Application Load Balancer](https://docs.aws.amazon.com/elasticloadbalancing/latest/application/create-https-listener.html), etc.

If, after configuring HTTPS, you find that the on-premises installation is still trying to use some HTTP URLs
or the web pages don't look right (e.g. styles are not loading correctly, images are oversize, etc),
you will likely need to specify the correct HTTPS URL via the `structurizr.url` property (see above).

### Logging

The `log4j2` framework is used for logging and, by default, the logging level is set to `info`.
If you're having an issue and would like to change the logging level:

1. Copy the [log4j2.properties](https://github.com/structurizr/onpremises/blob/main/src/main/resources/log4j2.properties) file into your Structurizr data directory.
2. Modify the logging levels as needed.
3. Restart your on-premises installation.

If you're specifically having issues with authentication via SAML, you can make the following changes:

1. Change line 19 to `logger.springSecurity.level = debug`