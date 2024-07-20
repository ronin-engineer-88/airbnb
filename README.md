# Airbnb | Ronin Engineer

## Introduction
- Description:
  - Developing a Homestay Booking System like Airbnb
  - A personal project for beginners
- [Youtube Playlist](https://www.youtube.com/playlist?list=PLzUU_6qfx-go8LjLudLCAXhu39xfS7biz)
- Author:
  - 🧑‍💻 Website: https://roninhub.com/
  - 💡 Page: [Ronin Engineer](https://fb.com/roninengineer)
  - 📚 Group: [System Design VN](https://fb.com/groups/systemdesign.vn)
- Status: `updating`

## Docs
- [Slides](https://drive.google.com/drive/folders/1AbyPadIeoFQJ25iQZwv1sLq-iIol-FNL?usp=sharing)
- [ERD](https://drive.google.com/file/d/14XOV1NbqnQVkuVTLMTmH7dwj1mQtTkfN/view)
- [Schema](./sql/schema.sql)

## Techniques
- Data Modeling
- API Design
- Codebase: Principles, Design Patterns
- Database
- JPA/Hibernate
- Caching
- Unit Testing
- Integration
- Security
- Deployment
- ...

## Tech Stack
- Programming Language: Java 21
- Framework: Spring Boot 3.3.0
- Database: Postgres 16
- Cache: Redis 7
- Unit Testing: JUnit 5
- Integration: VNPay Payment Gateway
- Security: JWT, Spring Security, ...
- Deployment
  - Container: Docker
  - CICD: Github Actions
  - Deployment: Cloud Server
- ...


<img src="./image/ronin_engineer_logo.png" width="200" >

Please feel free to give us feedbacks, contribution. <br>
Thank you so much! 🫶

## API cURL Sample

1. Search Homestay by Area
```bash
curl --location 'http://localhost:8080/api/v1/homestays?longitude=105.85093677113572&latitude=21.00331838574515&radius=1000&checkin_date=2024-07-04&checkout_date=2024-07-07&guests=2'
```
2. Book Homestay
```bash
curl --location 'http://localhost:8080/api/v1/bookings' \
--header 'Content-Type: application/json' \
--data '{
    "request_id": "qweq",
    "user_id": 1,
    "homestay_id": 1,
    "checkin_date": "2024-07-10",
    "checkout_date": "2024-07-13",
    "guests": 2,
    "note": "note test"
}'
```

ngrok http 8080 --domain=fox-funny-noticeably.ngrok-free.app


Sandbox VNPay Test
1. Thẻ nội địa
2. NCB
3. Nhập thông tin thẻ
```
9704198526191432198
NGUYEN VAN A
07/15
```
4. Nhập OTP: `123456`

