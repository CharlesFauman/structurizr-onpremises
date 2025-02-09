package com.structurizr.onpremises.component.workspace;

import com.structurizr.onpremises.domain.User;
import com.structurizr.onpremises.domain.UserType;
import com.structurizr.onpremises.util.DateUtils;
import com.structurizr.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class WorkspaceMetaData {

    public static final int LOCK_TIMEOUT_IN_MINUTES = 2;
    
    static final String NAME_PROPERTY = "name";
    static final String DESCRIPTION_PROPERTY = "description";
    static final String VERSION_PROPERTY = "version";
    static final String CLIENT_SIDE_ENCRYPTED_PROPERTY = "clientSideEncrypted";
    static final String LAST_MODIFIED_USER_PROPERTY = "lastModifiedUser";
    static final String LAST_MODIFIED_AGENT_PROPERTY = "lastModifiedAgent";
    static final String LAST_MODIFIED_DATE_PROPERTY = "lastModifiedDate";
    static final String REVISION_PROPERTY = "revision";
    static final String SIZE_PROPERTY = "size";
    static final String API_KEY_PROPERTY = "apiKey";
    static final String API_SECRET_PROPERTY = "apiSecret";
    static final String SHARING_TOKEN_PROPERTY = "sharingToken";
    static final String OWNER_PROPERTY = "owner";
    static final String LOCKED_USER_PROPERTY = "lockedUser";
    static final String LOCKED_AGENT_PROPERTY = "lockedAgent";
    static final String LOCKED_DATE_PROPERTY = "lockedDate";
    static final String READ_USERS_AND_ROLES_PROPERTY = "readUsers";
    static final String WRITE_USERS_AND_ROLES_PROPERTY = "writeUsers";

    private final long id;
    private String name = "";
    private String description = "";
    private String version;
    private long size;
    private boolean clientSideEncrypted = false;
    private String apiKey;
    private String apiSecret;
    private String sharingToken = "";

    private Date lastModifiedDate;
    private String lastModifiedUser;
    private String lastModifiedAgent;

    private String lockedUser;
    private String lockedAgent;
    private Date lockedDate;

    private String internalVersion;
    private long revision = 0;

    private boolean editable = false;

    private String owner;
    private final Set<String> readUsers = new LinkedHashSet<>();
    private final Set<String> writeUsers = new LinkedHashSet<>();

    public WorkspaceMetaData(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getSharingToken() {
        return sharingToken;
    }

    public void setSharingToken(String sharingToken) {
        this.sharingToken = sharingToken;
    }

    public String getSharingTokenTruncated() {
        return (sharingToken == null ? "" : sharingToken.substring(0, 6)) + "...";
    }

    public boolean isOpen() {
        return readUsers.size() == 0 && writeUsers.size() == 0;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public String getLastModifiedAgent() {
        return lastModifiedAgent;
    }

    public void setLastModifiedAgent(String lastModifiedAgent) {
        this.lastModifiedAgent = lastModifiedAgent;
    }

    public String getLockedUser() {
        return lockedUser;
    }

    public void setLockedUser(String lockedUser) {
        this.lockedUser = lockedUser;
    }

    public String getLockedAgent() {
        return lockedAgent;
    }

    public void setLockedAgent(String lockedAgent) {
        this.lockedAgent = lockedAgent;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public boolean isLocked() {
        return !StringUtils.isNullOrEmpty(lockedUser) && !DateUtils.isOlderThanXMinutes(lockedDate, LOCK_TIMEOUT_IN_MINUTES);
    }

    public boolean isLockedBy(String user, String agent) {
        return isLocked() && lockedUser.equals(user) && lockedAgent.equals(agent);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public double getSizeInMegaBytes() {
        return getSize()/(1024.0 * 1024.0);
    }

    public long getRevision() {
        return revision;
    }

    public void setRevision(long revision) {
        this.revision = revision;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public UserType getOwnerUserType() {
        return new UserType();
    }

    public boolean isClientEncrypted() {
        return this.clientSideEncrypted;
    }

    public void setClientSideEncrypted(boolean clientSideEncrypted) {
        this.clientSideEncrypted = clientSideEncrypted;
    }

    public boolean isShareable() {
        return !StringUtils.isNullOrEmpty(sharingToken);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<String> getReadUsers() {
        return new LinkedHashSet<>(readUsers);
    }

    public boolean isReadUser(User user) {
        if (user == null) {
            return false;
        } else {
            return user.isUserOrRole(readUsers);
        }
    }

    public void addReadUser(String user) {
        if (user != null) {
            user = user.trim();
            if (user.length() > 0) {
                readUsers.add(user.toLowerCase());
            }
        }
    }

    public void clearReadUsers() {
        readUsers.clear();
    }

    public Set<String> getWriteUsers() {
        return new LinkedHashSet<>(writeUsers);
    }

    public boolean isWriteUser(User user) {
        if (user == null) {
            return false;
        } else {
            return user.isUserOrRole(writeUsers);
        }
    }

    public void addWriteUser(String user) {
        if (user != null) {
            user = user.trim();
            if (user.length() > 0) {
                writeUsers.add(user.toLowerCase());
            }
        }
    }

    public void clearWriteUsers() {
        writeUsers.clear();
    }

    public boolean isOwner(User user) {
        return StringUtils.isNullOrEmpty(owner) || owner.equals(user.getUsername());
    }

    public String getInternalVersion() {
        return internalVersion;
    }

    public void setInternalVersion(String internalVersion) {
        this.internalVersion = internalVersion;
    }

    public boolean isActive() {
        return true;
    }

    public void addLock(String username, String agent) {
        this.lockedUser = username;
        this.lockedAgent = agent;
        this.lockedDate = new Date();
    }
    
    public void clearLock() {
        this.lockedUser = null;
        this.lockedAgent = null;
        this.lockedDate = null;
    }

    public static WorkspaceMetaData fromProperties(long workspaceId, Properties properties) {
        WorkspaceMetaData workspace = new WorkspaceMetaData(workspaceId);
        workspace.setName(properties.getProperty(NAME_PROPERTY));
        workspace.setDescription(properties.getProperty(DESCRIPTION_PROPERTY));
        workspace.setVersion(properties.getProperty(VERSION_PROPERTY));
        workspace.setClientSideEncrypted("true".equals(properties.getProperty(CLIENT_SIDE_ENCRYPTED_PROPERTY)));
        workspace.setLastModifiedUser(properties.getProperty(LAST_MODIFIED_USER_PROPERTY));
        workspace.setLastModifiedAgent(properties.getProperty(LAST_MODIFIED_AGENT_PROPERTY));
        try {
            String lastModifiedDateAsString = properties.getProperty(LAST_MODIFIED_DATE_PROPERTY);
            if (!StringUtils.isNullOrEmpty(lastModifiedDateAsString)) {
                workspace.setLastModifiedDate(DateUtils.parseIsoDate(lastModifiedDateAsString));
            } else {
                workspace.setLastModifiedDate(new Date(0));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        workspace.setRevision(Long.parseLong(properties.getProperty(REVISION_PROPERTY, "0")));
        workspace.setSize(Long.parseLong(properties.getProperty(SIZE_PROPERTY, "0")));
        workspace.setApiKey(properties.getProperty(API_KEY_PROPERTY, ""));
        workspace.setApiSecret(properties.getProperty(API_SECRET_PROPERTY, ""));
        workspace.setSharingToken(properties.getProperty(SHARING_TOKEN_PROPERTY, ""));
        workspace.setOwner(properties.getProperty(OWNER_PROPERTY, ""));

        workspace.setLockedUser(properties.getProperty(LOCKED_USER_PROPERTY, null));
        workspace.setLockedAgent(properties.getProperty(LOCKED_AGENT_PROPERTY, null));
        try {
            workspace.setLockedDate(DateUtils.parseIsoDate(properties.getProperty(LOCKED_DATE_PROPERTY)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String users = properties.getProperty(READ_USERS_AND_ROLES_PROPERTY);
        if (users != null) {
            String[] usersAsArray = users.split(",");
            for (String user : usersAsArray) {
                workspace.addReadUser(user);
            }
        }

        users = properties.getProperty(WRITE_USERS_AND_ROLES_PROPERTY);
        if (users != null) {
            String[] usersAsArray = users.split(",");
            for (String user : usersAsArray) {
                workspace.addWriteUser(user);
            }
        }

        return workspace;
    }

    public Properties toProperties() {
        Properties properties = new Properties();

        if (this.getName() != null) {
            properties.setProperty(NAME_PROPERTY, this.getName());
        } else {
            properties.setProperty(NAME_PROPERTY, "");
        }

        if (this.getDescription() != null) {
            properties.setProperty(DESCRIPTION_PROPERTY, this.getDescription());
        } else {
            properties.setProperty(DESCRIPTION_PROPERTY, "");
        }

        if (this.getVersion() != null) {
            properties.setProperty(VERSION_PROPERTY, this.getVersion());
        }

        properties.setProperty(CLIENT_SIDE_ENCRYPTED_PROPERTY, "" + this.isClientEncrypted());

        if (this.getLastModifiedUser() != null) {
            properties.setProperty(LAST_MODIFIED_USER_PROPERTY, this.getLastModifiedUser());
        } else {
            properties.setProperty(LAST_MODIFIED_USER_PROPERTY, "");
        }

        if (this.getLastModifiedAgent() != null) {
            properties.setProperty(LAST_MODIFIED_AGENT_PROPERTY, this.getLastModifiedAgent());
        } else {
            properties.setProperty(LAST_MODIFIED_AGENT_PROPERTY, "");
        }

        if (this.getLastModifiedDate() != null) {
            properties.setProperty(LAST_MODIFIED_DATE_PROPERTY, DateUtils.formatIsoDate(this.getLastModifiedDate()));
        } else {
            properties.setProperty(LAST_MODIFIED_DATE_PROPERTY, "");
        }

        properties.setProperty(REVISION_PROPERTY, "" + this.getRevision());

        if (!StringUtils.isNullOrEmpty(this.getOwner())) {
            properties.setProperty(OWNER_PROPERTY, this.getOwner());
        }
        properties.setProperty(READ_USERS_AND_ROLES_PROPERTY, toCommaSeparatedString(this.getReadUsers()));
        properties.setProperty(WRITE_USERS_AND_ROLES_PROPERTY, toCommaSeparatedString(this.getWriteUsers()));

        properties.setProperty(SIZE_PROPERTY, "" + this.getSize());

        properties.setProperty(API_KEY_PROPERTY, this.getApiKey());
        properties.setProperty(API_SECRET_PROPERTY, this.getApiSecret());

        if (!StringUtils.isNullOrEmpty(getSharingToken())) {
            properties.setProperty(SHARING_TOKEN_PROPERTY, this.getSharingToken());
        }

        if (this.getLockedUser() != null) {
            properties.setProperty(LOCKED_USER_PROPERTY, this.getLockedUser());
        }

        if (this.getLockedAgent() != null) {
            properties.setProperty(LOCKED_AGENT_PROPERTY, this.getLockedAgent());
        }

        if (this.getLockedDate() != null) {
            properties.setProperty(LOCKED_DATE_PROPERTY, DateUtils.formatIsoDate(this.getLockedDate()));
        } else {
            properties.setProperty(LOCKED_DATE_PROPERTY, "");
        }

        return properties;
    }

    private String toCommaSeparatedString(Set<String> strings) {
        StringBuilder buf = new StringBuilder();
        if (strings != null) {
            for (String username : strings) {
                buf.append(username);
                buf.append(",");
            }
        }

        if (buf.toString().endsWith(",")) {
            return buf.substring(0, buf.length()-1);
        } else {
            return buf.toString();
        }
    }

}