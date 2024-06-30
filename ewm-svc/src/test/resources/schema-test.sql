CREATE TABLE IF NOT EXISTS users (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(254) NOT NULL,
  CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  pinned BOOLEAN NOT NULL,
  CONSTRAINT uq_compilation_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS events (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  initiator_id INTEGER NOT NULL,
  annotation VARCHAR(2000) NOT NULL,
  category_id INTEGER NOT NULL,
  description VARCHAR(7000) NOT NULL,
  event_date TIMESTAMP NOT NULL,
  lat DOUBLE PRECISION NOT NULL,
  lon DOUBLE PRECISION NOT NULL,
  paid BOOLEAN NOT NULL,
  participant_limit INTEGER NOT NULL,
  request_moderation BOOLEAN NOT NULL,
  title VARCHAR(120) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  state VARCHAR(50) NOT NULL,
  published_on TIMESTAMP,
  CONSTRAINT fk_events_to_users FOREIGN KEY(initiator_id) REFERENCES users(id),
  CONSTRAINT fk_events_to_categories FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  compilation_id INTEGER NOT NULL,
  event_id INTEGER NOT NULL,
  CONSTRAINT fk_compilations_events_to_compilations FOREIGN KEY(compilation_id) REFERENCES compilations(id),
  CONSTRAINT fk_compilations_events_to_events FOREIGN KEY(event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS requests (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  event_id INTEGER NOT NULL,
  requester_id INTEGER NOT NULL,
  created TIMESTAMP NOT NULL,
  status VARCHAR(50) NOT NULL,
  CONSTRAINT fk_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id),
  CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS locations (
  id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  lat DOUBLE PRECISION NOT NULL,
  lon DOUBLE PRECISION NOT NULL,
  radius DOUBLE PRECISION NOT NULL,
  CONSTRAINT uq_location_name UNIQUE (name)
);