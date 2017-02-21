package cn.zheteng123.m_volunteer.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 2017/2/21.
 */


public class Token {

    /**
     * access_token : c8168c9c-a971-435b-bf30-d91e1c37e2e3
     * token_type : bearer
     * refresh_token : a58aac45-ff86-40e4-928b-e70aac6d7abd
     * expires_in : 43187
     * scope : read write
     */

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private int expiresIn;

    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
