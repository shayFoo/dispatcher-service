# Build
custom_build(
    # Name of the container image
    ref = 'dispatcher-service',
    # Command to build the image - using env parameter for cross-platform compatibility
    command='gradlew jibDockerBuild --image %EXPECTED_REF%',
    # files to watch for changes
    deps=['build.gradle.kts', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yaml', 'k8s/service.yaml'])

# Manage
k8s_resource(
    'dispatcher-service'
)