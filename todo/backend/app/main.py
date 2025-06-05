from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api.routes import todos
from app.core.database import engine
from app.models import todo as todo_model

# Create database tables
todo_model.Base.metadata.create_all(bind=engine)

# Create FastAPI app
app = FastAPI(
    title="Todo API",
    description="A todo app with 5-layer architecture",
    version="2.0.0"
)

# Configure CORS
origins = [
    "http://localhost:5173"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routers
app.include_router(todos.router, prefix="/api")

@app.get("/")
def root():
    return {"message": "Welcome to Todo API v2.0"}