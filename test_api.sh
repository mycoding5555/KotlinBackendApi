#!/bin/bash

# API Testing Script for Student Attendance System
# Base URL for the API
BASE_URL="http://localhost:8080/api"

echo "=== Testing Student Attendance API ==="
echo "Base URL: $BASE_URL"
echo ""

# Test login endpoint
echo "1. Testing teacher login..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
        "username": "john_doe",
        "password": "password123"
    }')

echo "Login Response: $LOGIN_RESPONSE"

# Extract JWT token (if login successful)
if [[ $LOGIN_RESPONSE == *"token"* ]]; then
    TOKEN=$(echo $LOGIN_RESPONSE | sed 's/.*"token":"\([^"]*\)".*/\1/')
    echo "JWT Token extracted: ${TOKEN:0:50}..."
    echo ""
    
    # Test authenticated endpoints
    echo "2. Testing teachers endpoint with authentication..."
    curl -s -X GET "$BASE_URL/teachers" \
        -H "Authorization: Bearer $TOKEN" | head -c 200
    echo ""
    echo ""
    
    echo "3. Testing students endpoint with authentication..."
    curl -s -X GET "$BASE_URL/students" \
        -H "Authorization: Bearer $TOKEN" | head -c 200
    echo ""
    echo ""
    
    echo "4. Testing groups endpoint with authentication..."
    curl -s -X GET "$BASE_URL/groups" \
        -H "Authorization: Bearer $TOKEN" | head -c 200
    echo ""
    echo ""
    
    echo "5. Testing subjects endpoint with authentication..."
    curl -s -X GET "$BASE_URL/subjects" \
        -H "Authorization: Bearer $TOKEN" | head -c 200
    echo ""
    echo ""
    
else
    echo "Login failed or no token received"
fi

# Test public endpoints (should work without authentication)
echo "6. Testing public root endpoint..."
curl -s -X GET "$BASE_URL/"
echo ""
echo ""

echo "=== API Testing Complete ==="