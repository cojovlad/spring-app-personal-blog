-- 1. Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS personal_blog;

-- 2. Use the created database
USE personal_blog;

-- 3. Create 'user' table
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

-- 4. Create 'role' table
CREATE TABLE IF NOT EXISTS role (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL UNIQUE
    );

-- 5. Create 'user_roles' table (Many-to-Many relationship between user and role)
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
    );

-- 6. Create 'article' table
CREATE TABLE IF NOT EXISTS article (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    published_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
    );

-- 7. Create 'persistent_logins' table
CREATE TABLE persistent_logins (
                                   username VARCHAR(64) NOT NULL,
                                   series VARCHAR(64) PRIMARY KEY,
                                   token VARCHAR(64) NOT NULL,
                                   last_used TIMESTAMP NOT NULL
);

-- Optional: Insert initial data (for testing/admin)
-- Insert roles if not already added
INSERT INTO role (name) VALUES ('ROLE_ADMIN') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO role (name) VALUES ('ROLE_USER') ON DUPLICATE KEY UPDATE id=id;

-- Insert admin user (make sure the password is hashed)
INSERT INTO user (username, password)
VALUES ('admin', 'adminpassword'); -- Use hashed password here

-- Link admin user to admin role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM user u
         JOIN role r ON r.name = 'ROLE_ADMIN'
WHERE u.username = 'admin';

-- Insert normal user (make sure the password is hashed)
INSERT INTO user (username, password)
VALUES ('normaluser', 'normaluserpassword'); -- Use hashed password here

-- Link normal user to user role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM user u
         JOIN role r ON r.name = 'ROLE_USER'
WHERE u.username = 'normaluser';

-- Insert articles for testing
-- Insert articles for testing
INSERT INTO article (title, content, published_at, user_id) VALUES
                                                                ('Introduction to Spring Boot',
                                                                 'Spring Boot makes it easy to create stand-alone applications.',
                                                                 NOW(),
                                                                 1), -- admin

                                                                ('Understanding JPA in Spring',
                                                                 'JPA simplifies database interactions in Spring applications.',
                                                                 NOW(),
                                                                 1), -- admin

                                                                ('REST API Design Best Practices',
                                                                 'Learn about designing scalable RESTful APIs.',
                                                                 NOW(),
                                                                 2), -- normaluser

                                                                ('Spring Security Basics',
                                                                 'An introduction to securing your Spring Boot application.',
                                                                 NOW(),
                                                                 2); -- normaluser
