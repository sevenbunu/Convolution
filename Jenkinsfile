node {
    stage('Checkout') {
        checkout scm
    }

    stage('Lint') {
        String linter = 'clang-tidy'
        sh "${linter}"
    }
}
