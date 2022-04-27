# HelloKospring

[![GitHub Super-Linter](https://github.com/KimSoungRyoul/HelloKopring/actions/workflows/super_linter.yaml/badge.svg?branch=main)](https://github.com/marketplace/actions/super-linter)

## QuickStart

```shell
git clone git@github.com:KimSoungRyoul/HelloKopring.git
```

```shell
docker-compose -f docker/docker-compose.local.yaml -p "hello-kopring" up -d
```

```shell
gradle bootRun -Dspring-boot.run.profiles=local
```

### ktlint 로컬 환경에 걸기

### 1. pre-commit

#### jenv 설치
```shell
brew tap adoptopenjdk/openjdk
brew search adoptopenjdk
brew install adoptopenjdk16

jenv add adoptopenjdk/openjdk/adoptopenjdk16
jenv add /Library/Java/JavaVirtualMachines/adoptopenjdk-16.jdk/Contents/Home/
jenv global openjdk64-16.0.1
java --version
```
#### pre-commit hook 걸기 (recommend)

```shell
brew install pre-commit
cd /{프로젝트경로}/HelloKopring
pre-commit install  # pre-commit install 수행하는 시점에 해당 프로젝트 .git파일에 커밋훅이 전부 걸립니다.
pre-commit run # pre-commit 커맨드로 직접 수행하고싶을때
```




커맨드로 ktlint에 어긋나는 코드 강제 변환

```shell
./gradlew ktlintFormat
```

커밋훅 걸기

```shell
./gradlew addKtlintFormatGitPreCommitHook
```

Idea에 걸기

```shell
./gradlew ktlintApplyToIdea
```



### GitHub cli 사용법
