package com.amigoscode.chohort2.carRental.external.s3;

import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.auth.credentials.internal.LazyAwsCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.profiles.ProfileFileSupplier;
import software.amazon.awssdk.utils.SdkAutoCloseable;
import software.amazon.awssdk.utils.ToString;
import software.amazon.awssdk.utils.builder.CopyableBuilder;
import software.amazon.awssdk.utils.builder.ToCopyableBuilder;

import java.util.Optional;
import java.util.function.Supplier;

@SdkPublicApi
public final class CustomCredentialsProvider implements AwsCredentialsProvider, SdkAutoCloseable,
        ToCopyableBuilder<CustomCredentialsProvider.Builder, CustomCredentialsProvider> {

    private static final CustomCredentialsProvider CUSTOM_CREDENTIALS_PROVIDER = new CustomCredentialsProvider(builder());

    private final LazyAwsCredentialsProvider providerChain;

    private final Supplier<ProfileFile> profileFile;

    private final String profileName;

    private final Boolean reuseLastProviderEnabled;

    private final Boolean asyncCredentialUpdateEnabled;

    /**
     * @see #builder()
     */
    private CustomCredentialsProvider(CustomCredentialsProvider.Builder builder) {
        this.profileFile = builder.profileFile;
        this.profileName = builder.profileName;
        this.reuseLastProviderEnabled = builder.reuseLastProviderEnabled;
        this.asyncCredentialUpdateEnabled = builder.asyncCredentialUpdateEnabled;
        this.providerChain = createChain(builder);
    }

    /**
     * Create an instance of the {@link CustomCredentialsProvider} using the custom configuration. Configuration can be
     * specified by creating an instance using the {@link #builder()}.
     */
    public static CustomCredentialsProvider create() {
        return CUSTOM_CREDENTIALS_PROVIDER;
    }

    /**
     * Create the custom credential chain using the configuration in the provided builder.
     */
    private static LazyAwsCredentialsProvider createChain(CustomCredentialsProvider.Builder builder) {
        boolean asyncCredentialUpdateEnabled = builder.asyncCredentialUpdateEnabled;
        boolean reuseLastProviderEnabled = builder.reuseLastProviderEnabled;

        return LazyAwsCredentialsProvider.create(() -> {
            AwsCredentialsProvider[] credentialsProviders = new AwsCredentialsProvider[]{
                    SystemPropertyCredentialsProvider.create(),
                    EnvironmentVariableCredentialsProvider.create(),
                    WebIdentityTokenFileCredentialsProvider.create(),
                    ProfileCredentialsProvider.builder()
                            .profileFile(builder.profileFile)
                            .profileName(builder.profileName)
                            .build(),
                    ContainerCredentialsProvider.builder()
                            .asyncCredentialUpdateEnabled(asyncCredentialUpdateEnabled)
                            .build(),
                    InstanceProfileCredentialsProvider.builder()
                            .asyncCredentialUpdateEnabled(asyncCredentialUpdateEnabled)
                            .profileFile(builder.profileFile)
                            .profileName(builder.profileName)
                            .build()
            };

            return AwsCredentialsProviderChain.builder()
                    .reuseLastProviderEnabled(reuseLastProviderEnabled)
                    .credentialsProviders(credentialsProviders)
                    .build();
        });
    }

    /**
     * Get a builder for defining a {@link CustomCredentialsProvider} with custom configuration.
     */
    public static CustomCredentialsProvider.Builder builder() {
        return new CustomCredentialsProvider.Builder();
    }

    @Override
    public AwsCredentials resolveCredentials() {
        return providerChain.resolveCredentials();
    }

    @Override
    public void close() {
        providerChain.close();
    }

    @Override
    public String toString() {
        return ToString.builder("CustomCredentialsProvider")
                .add("providerChain", providerChain)
                .build();
    }

    @Override
    public CustomCredentialsProvider.Builder toBuilder() {
        return new CustomCredentialsProvider.Builder(this);
    }

    /**
     * Configuration that defines the {@link CustomCredentialsProvider}'s behavior.
     */
    public static final class Builder implements CopyableBuilder<CustomCredentialsProvider.Builder, CustomCredentialsProvider> {
        private Supplier<ProfileFile> profileFile;
        private String profileName;
        private Boolean reuseLastProviderEnabled = true;
        private Boolean asyncCredentialUpdateEnabled = false;

        /**
         * Created with {@link #builder()}.
         */
        private Builder() {
        }

        private Builder(CustomCredentialsProvider credentialsProvider) {
            this.profileFile = credentialsProvider.profileFile;
            this.profileName = credentialsProvider.profileName;
            this.reuseLastProviderEnabled = credentialsProvider.reuseLastProviderEnabled;
            this.asyncCredentialUpdateEnabled = credentialsProvider.asyncCredentialUpdateEnabled;
        }

        public CustomCredentialsProvider.Builder profileFile(ProfileFile profileFile) {
            return profileFile(Optional.ofNullable(profileFile)
                    .map(ProfileFileSupplier::fixedProfileFile)
                    .orElse(null));
        }

        public CustomCredentialsProvider.Builder profileFile(Supplier<ProfileFile> profileFileSupplier) {
            this.profileFile = profileFileSupplier;
            return this;
        }

        public CustomCredentialsProvider.Builder profileName(String profileName) {
            this.profileName = profileName;
            return this;
        }

        /**
         * Controls whether the provider should reuse the last successful credentials provider in the chain. Reusing the last
         * successful credentials provider will typically return credentials faster than searching through the chain.
         *
         * <p>By default, this is enabled.</p>
         */
        public CustomCredentialsProvider.Builder reuseLastProviderEnabled(Boolean reuseLastProviderEnabled) {
            this.reuseLastProviderEnabled = reuseLastProviderEnabled;
            return this;
        }

        /**
         * Configure whether this provider should fetch credentials asynchronously in the background. If this is true, threads are
         * less likely to block when {@link #resolveCredentials()} is called, but additional resources are used to maintain the
         * provider.
         *
         * <p>By default, this is disabled.</p>
         */
        public CustomCredentialsProvider.Builder asyncCredentialUpdateEnabled(Boolean asyncCredentialUpdateEnabled) {
            this.asyncCredentialUpdateEnabled = asyncCredentialUpdateEnabled;
            return this;
        }

        /**
         * Create a {@link CustomCredentialsProvider} using the configuration defined in this builder.
         */
        @Override
        public CustomCredentialsProvider build() {
            return new CustomCredentialsProvider(this);
        }
    }
}
