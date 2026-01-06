import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';
import type { Student } from './types'; // Import Type
import StudentForm from './components/StudentForm'; // Import Component
import StudentList from './components/StudentList'; // Import Component

function App() {
  //State Data 
  const [students, setStudents] = useState<Student[]>([]);
  const [formData, setFormData] = useState<Student>({
    firstName: '',
    lastName: '',
    email: '',
    gpa: ''
  });
  const [editingId, setEditingId] = useState<number | null>(null);

  const API_URL = 'http://localhost:8080/api/students';

  //Logic Functions

  useEffect(() => {
    loadStudents();
  }, []);

  const loadStudents = async () => {
    try {
      const result = await axios.get<Student[]>(API_URL);
      setStudents(result.data);
    } catch (error) {
      console.error("Error connecting to backend:", error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingId) {
        await axios.put(`${API_URL}/${editingId}`, formData);
        setEditingId(null);
      } else {
        await axios.post(API_URL, formData);
      }
      loadStudents();
      resetForm();
    } catch (error) {
      console.error("Error saving student:", error);
    }
  };

  const handleEdit = (student: Student) => {
    if (student.id) setEditingId(student.id);
    setFormData({
      firstName: student.firstName,
      lastName: student.lastName,
      email: student.email,
      gpa: student.gpa
    });
  };

  const handleDelete = async (id: number) => {
    if(!confirm("Are you sure?")) return;
    try {
      await axios.delete(`${API_URL}/${id}`);
      loadStudents();
    } catch (error) {
      console.error("Error deleting student:", error);
    }
  };

  // Helper to clear form
  const resetForm = () => {
    setEditingId(null);
    setFormData({ firstName: '', lastName: '', email: '', gpa: '' });
  };

  // UI
  return (
    <div className="container">
      <h1>Student Management System</h1>
      <div className="content">
        
        {/* Pass functions and data to the Form Component */}
        <StudentForm 
          formData={formData}
          editingId={editingId}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
          handleCancel={resetForm}
        />

        {/* Pass data to the List Component */}
        <StudentList 
          students={students}
          handleEdit={handleEdit}
          handleDelete={handleDelete}
        />

      </div>
    </div>
  );
}

export default App;