# Digital Signage CMS

A full-stack content management system for digital signage, built as an internship skills-test project. Users register/log in, create signs, upload media (images/videos), and each sign is published to a public URL that displays scheduled content.

**Stack:** Spring Boot (Java) · MySQL · Angular · JWT authentication · Cloudinary (media storage)

## Project Structure

```
DigitalSignage/
├── DigitalSignageBackEnd/     # Spring Boot API
│   └── src/main/java/io/github/ahmedktarii/Digitalsingage/
│       ├── Controllers/       # REST endpoints
│       ├── Services/          # Business logic
│       ├── Repositories/      # Spring Data JPA repos
│       ├── Entities/          # JPA entities (DB models)
│       ├── DTOS/              # Request/response objects
│       ├── Configuration/     # App config (e.g. CloudConfig)
│       └── Utils/             # Helpers (file hashing, string utils)
├── DigitalSignageFrontEnd/    # Angular app
│   └── src/app/
│       ├── login/ register/   # Auth pages
│       ├── managing-signs/    # Sign creation & browsing
│       ├── menu/ footer/      # Layout components
│       ├── profile/ logout/
│       └── Services/          # Auth service, route guards
└── DB_Diagram.png             # Database schema diagram
```

## Features

- **User accounts** — registration and login with JWT-based authentication; users are identified by email (unique) and also get a human-readable code like .
- **Sign creation** — users create signs either as "publish immediately" or "scheduled," with status tracked via a `signStatus` enum (Online / Offline / Disabled).
- **Public sign URLs** — each sign gets a unique `slug`, so it's publicly viewable at a URL like `domain.com/s/{slug}` without requiring login.
- **Media upload** — images and videos are uploaded and stored via Cloudinary, linked to signs.
- **Scheduling** — a `Schedule` entity links signs to media with `start_time`/`end_time`; the backend serves whichever media is currently active with a `WHERE NOW() BETWEEN start_time AND end_time` query, and the frontend polls periodically to swap displayed content — no server-side background jobs needed.

## Data Model

Core entities (see `DB_Diagram.png` for the full schema):

- **User** — id, username, email (unique), userCode (unique, e.g. `U01`), password (hashed), role (Admin/Client), createdAt
- **Sign** — id, owner (FK to User), title, slug (unique, public URL token), status, createdAt
- **Media** — uploaded images/videos linked to a sign
- **Schedule** — links a Sign to Media with a start/end time window

## Backend

- Java + Spring Boot, built with Maven
- Spring Data JPA / Hibernate for persistence against MySQL
- Layered architecture: Controller → Service → Repository → Entity
- Lombok for boilerplate (getters/setters/constructors/builders)
- JWT for stateless authentication(Currently i didnt add it )
- Cloudinary SDK for media storage

### Running the backend

```bash
cd DigitalSignageBackEnd
./mvnw spring-boot:run
```

Requires a local MySQL instance and a configured `application.properties` (DB URL/credentials, Cloudinary keys, JWT secret) — not committed to version control.
```
        spring.application.name=DegitalSignage
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        logging.level.org.springframework.security=DEBUG
        spring.datasource.url=jdbc:{MYSQL_DATABASE_URL}
        spring.datasource.username={MYSQL_DATABASE_USERNAME}
        spring.datasource.password={MYSQL_DATEBASE_PASSWORD}
        cloudinary.cloud-name={CLOUDINARY_CLOUS_NAME}
        cloudinary.api-key={CLOUDINARY_API_KEY}
        cloudinary.api-secret={CLOUDINARY_API_SECRET}
        #the max file and request size is customizable 
        spring.servlet.multipart.max-file-size=25MB
        spring.servlet.multipart.max-request-size=25MB

```
## Frontend

- Angular (v22), with SSR support
- Standalone components: `login`, `register`, `managing-signs`, `menu`, `profile`, `logout`, `welcome`, `error`
- Auth handled via `cookies`  and Angular route guards

### Running the frontend

```bash
cd DigitalSignageFrontEnd
npm install
ng serve
```

## Status

Actively in development
working on progress Features : the signage system with diplaying the sign at the correct time 
---
*Built by Ahmed ktari as a self-directed internship skills test — learning full stack web development.*