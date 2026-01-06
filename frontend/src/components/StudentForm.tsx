import React from 'react';
import type { Student } from '../types'; // Import the definition


interface Props {// Define what data this component needs from the Parent (App.tsx)
  formData: Student;
  editingId: number | null;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  handleSubmit: (e: React.FormEvent) => void;
  handleCancel: () => void;
}

const StudentForm: React.FC<Props> = ({ 
  formData, 
  editingId, 
  handleChange, 
  handleSubmit, 
  handleCancel 
}) => {
  return (
    <div className="form-section">
      <h3>{editingId ? 'Edit Student' : 'Add New Student'}</h3>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="firstName">First Name</label>
          <input 
            id="firstName" 
            name="firstName" 
            value={formData.firstName} 
            onChange={handleChange} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="lastName">Last Name</label>
          <input 
            id="lastName"
            name="lastName" 
            value={formData.lastName} 
            onChange={handleChange} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input 
            id="email"
            name="email" 
            type="email" 
            value={formData.email} 
            onChange={handleChange} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="gpa">GPA</label>
          <input 
            id="gpa"
            name="gpa" 
            type="number" 
            step="0.01" 
            value={formData.gpa} 
            onChange={handleChange} 
            required 
          />
        </div>
        <div className="button-group">
          <button type="submit" className={editingId ? "btn-update" : "btn-add"}>
            {editingId ? 'Update' : 'Add'}
          </button>
          
          {editingId && (
            <button type="button" className="btn-cancel" onClick={handleCancel}>
              Cancel
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default StudentForm;