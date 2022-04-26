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

커맨드로 체크

```shell
./gradlew ktlintCheck
```

커밋훅 걸기

```shell
./gradlew addKtlintCheckGitPreCommitHook
```

Idea에 걸기

```shell
./gradlew ktlintApplyToIdea
```
