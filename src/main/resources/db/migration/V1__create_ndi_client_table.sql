-- Create NDI Client Details Table
CREATE TABLE IF NOT EXISTS ndi_client_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id VARCHAR(50) NOT NULL UNIQUE,
    client_name VARCHAR(255) NOT NULL,
    client_redirect_url VARCHAR(500) NOT NULL,
    client_status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Index on CLIENT_ID for faster lookups
CREATE INDEX idx_client_id ON ndi_client_detail(client_id);
