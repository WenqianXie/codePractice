# Simple Todo App

A minimal full-stack Todo application with React, TypeScript, and FastAPI.

## Tech Stack

- **Frontend**: React, TypeScript, CSS Modules
- **Backend**: Python, FastAPI

## Running the App

### Backend Setup

```bash
cd backend
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn app.main:app --reload
```

Backend runs at http://localhost:8000

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at http://localhost:5173 (or check terminal output)

## Features

Create, edit, complete, and delete tasks
Simple and clean interface

## Project Structure

Backend: FastAPI server with in-memory storage
Frontend: React components with TypeScript type safety

TodoList: Manages state and API calls
TodoItem: Displays individual todos

## API Endpoints

GET /todos/: Get all todos
POST /todos/: Create a todo
PUT /todos/{id}: Update a todo
DELETE /todos/{id}: Delete a todo
