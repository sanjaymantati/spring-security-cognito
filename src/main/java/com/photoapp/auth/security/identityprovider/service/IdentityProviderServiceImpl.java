package com.photoapp.auth.security.identityprovider.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.photoapp.auth.security.exception.CustomException;
import com.photoapp.auth.security.identityprovider.dto.request.LoginUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.request.SignUpUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.request.UpdateUserPasswordRequestDto;
import com.photoapp.auth.security.identityprovider.dto.response.LoginResponseDto;
import com.photoapp.auth.security.identityprovider.dto.response.SignUpUserResponseDto;
import com.photoapp.auth.security.identityprovider.dto.response.UpdateUserPasswordResponseDto;
import com.photoapp.auth.security.identityprovider.enums.IdentityChallenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.photoapp.auth.utility.Utility.notEmpty;

@Service
@Slf4j
public class IdentityProviderServiceImpl implements IdentityProviderService {


    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    @Value(value = "${aws.cognito.clientSecret}")
    private String clientSecret;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Override
    public SignUpUserResponseDto createUser(SignUpUserRequestDto request) {
        SignUpUserResponseDto responseDto = new SignUpUserResponseDto();

        AttributeType emailAttr = new AttributeType().withName("email").withValue(request.getEmail());
        AttributeType emailVerifiedAttr = new AttributeType().withName("email_verified").withValue("true");

        AdminCreateUserRequest userRequest = new AdminCreateUserRequest().withUserPoolId(this.userPoolId).withUsername(request.getUsername()).withTemporaryPassword(request.getPassword()).withUserAttributes(emailAttr, emailVerifiedAttr).withMessageAction(MessageActionType.SUPPRESS).withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

        AdminCreateUserResult createUserResult = this.cognitoClient.adminCreateUser(userRequest);
        if (notEmpty(createUserResult.getUser().getUserStatus()) && "FORCE_CHANGE_PASSWORD".equals(createUserResult.getUser().getUserStatus()))
        {
            responseDto.setChallenge(IdentityChallenge.UPDATE_PASSWORD);
        }
        return responseDto;
    }

    @Override
    public UpdateUserPasswordResponseDto updateUserPassword(UpdateUserPasswordRequestDto requestDto) {
        UpdateUserPasswordResponseDto responseDto = new UpdateUserPasswordResponseDto();
        AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest().withUsername(requestDto.getUsername()).withUserPoolId(userPoolId).withPassword(requestDto.getPassword()).withPermanent(true);
        AdminSetUserPasswordResult adminSetUserPasswordResult = cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
        return responseDto;
    }

    @Override
    public LoginResponseDto loginUser(LoginUserRequestDto requestDto) {
        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", requestDto.getUsername());
        authParams.put("PASSWORD", requestDto.getPassword());
        authParams.put("SECRET_HASH", this.calculateSecretHash(clientId, clientSecret, requestDto.getUsername()));
        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId)
                .withUserPoolId(userPoolId)
                .withAuthParameters(authParams);
        AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setChallenge(convertToIdentityChallenge(result.getChallengeName()));
        AuthenticationResultType authenticationResult = result.getAuthenticationResult();
        if (notEmpty(result.getAuthenticationResult())) {
            responseDto.setAccessToken(authenticationResult.getAccessToken());
            responseDto.setIdToken(authenticationResult.getIdToken());
            responseDto.setRefreshToken(authenticationResult.getRefreshToken());
            responseDto.setExpiresIn(authenticationResult.getExpiresIn());
            responseDto.setTokenType(authenticationResult.getTokenType());
        }
        return responseDto;
    }


    private IdentityChallenge convertToIdentityChallenge(String str) {
        if (notEmpty(str))
            return IdentityChallenge.valueOf(str);
        return null;
    }


    public String calculateSecretHash(String userPoolclientId, String userPoolclientSecret, String userName) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(userPoolclientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolclientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            throw new CustomException("Error while creating client secret hash", e);
        }
    }
}
