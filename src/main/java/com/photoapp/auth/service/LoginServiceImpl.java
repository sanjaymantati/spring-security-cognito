package com.photoapp.auth.service;

import com.photoapp.auth.dto.request.UserSignInRequestDto;
import com.photoapp.auth.dto.response.UserSignInResponseDto;
import com.photoapp.auth.exception.CustomException;
import com.photoapp.auth.exception.InvalidCredentialsException;
import com.photoapp.auth.repository.UserRepository;
import com.photoapp.auth.security.identityprovider.dto.request.LoginUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.response.LoginResponseDto;
import com.photoapp.auth.security.identityprovider.service.IdentityProviderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.photoapp.auth.utility.Utility.notEmpty;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final IdentityProviderService identityProviderService;
    private final UserRepository userRepository;

    @Override
    public UserSignInResponseDto loginUser(UserSignInRequestDto request) {

        this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials."));
        LoginResponseDto responseDto = this.identityProviderService.loginUser(new LoginUserRequestDto(request.getUsername(), request.getPassword()));

        if(!notEmpty(responseDto.getChallenge()))
            return new UserSignInResponseDto(responseDto);

        return new UserSignInResponseDto(responseDto);


        /*UserSignInResponseDto userSignInResponse = new UserSignInResponseDto();

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", request.getUsername());
        authParams.put("PASSWORD", request.getPassword());
        authParams.put("SECRET_HASH", this.calculateSecretHash(clientId, clientSecret, request.getUsername()));
        Map<String, String> clientMetadata = new HashMap<>();
        clientMetadata.put("clientSecret", clientSecret);
        clientMetadata.put("SECRET_HASH", this.calculateSecretHash(clientId, clientSecret, request.getUsername()));
        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId)
                .withClientMetadata(clientMetadata)
                .withUserPoolId(userPoolId)
                .withAuthParameters(authParams);

        try {
            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

            AuthenticationResultType authenticationResult = null;

            if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {

                System.out.println("Challenge Name is " + result.getChallengeName());

                if (result.getChallengeName().contentEquals("NEW_PASSWORD_REQUIRED")) {
                    if (request.getPassword() == null) {
                        throw new CustomException("User must change password " + result.getChallengeName());

                    } else {

                        final Map<String, String> challengeResponses = new HashMap<>();
                        challengeResponses.put("USERNAME", request.getUsername());
                        challengeResponses.put("PASSWORD", request.getPassword());
                        // add new password
                        challengeResponses.put("NEW_PASSWORD", request.getPassword());

                        final AdminRespondToAuthChallengeRequest adminRespondToAuthChallengeRequest = new AdminRespondToAuthChallengeRequest().withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED).withChallengeResponses(challengeResponses).withClientId(clientId).withUserPoolId(userPoolId).withSession(result.getSession());

                        AdminRespondToAuthChallengeResult resultChallenge = cognitoClient.adminRespondToAuthChallenge(adminRespondToAuthChallengeRequest);
                        authenticationResult = resultChallenge.getAuthenticationResult();

                        userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
                        userSignInResponse.setIdToken(authenticationResult.getIdToken());
                        userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                        userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                        userSignInResponse.setTokenType(authenticationResult.getTokenType());
                    }

                } else {
                    throw new CustomException("User has other challenge " + result.getChallengeName());
                }
            } else {

                System.out.println("User has no challenge");
                authenticationResult = result.getAuthenticationResult();

                userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
                userSignInResponse.setIdToken(authenticationResult.getIdToken());
                userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                userSignInResponse.setTokenType(authenticationResult.getTokenType());
            }

        } catch (InvalidParameterException e) {
            e.printStackTrace();
            throw new CustomException(e.getErrorMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
//        cognitoClient.shutdown();
        return userSignInResponse;*/

    }

}
