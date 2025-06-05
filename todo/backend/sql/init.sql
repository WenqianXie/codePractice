-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create todos table
CREATE TABLE IF NOT EXISTS todos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(100) NOT NULL,
    details VARCHAR(200),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Insert test data
INSERT INTO todos (title, details, completed) VALUES 
    ('Learn FastAPI', 'Complete 5-layer architecture refactoring', false),
    ('Setup Database', 'Configure PostgreSQL with Docker', true),
    ('Write Tests', 'Add unit tests and integration tests', false);