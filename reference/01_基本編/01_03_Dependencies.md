# Dependencies

## 基本
    implementation 'org.springframework.boot:spring-boot-starter-web'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    compile "org.thymeleaf.extras:thymeleaf-extras-springsecurity5"
    compile 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-aop'
    
## DB関連
    runtimeOnly 'mysql:mysql-connector-java'
    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
## webjars
    compile 'org.webjars:jquery:3.5.0'
    compile 'org.webjars:bootstrap:4.3.1'
    compile 'org.aspectj:aspectjweaver:1.7.3'


解説は後日追記
