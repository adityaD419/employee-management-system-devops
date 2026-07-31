// ContactUs.js
import React, { useState } from 'react';


const Contact = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        // Send form data to the backend API
        try {
            const response = await fetch('http://localhost:8080/admin/contact', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData), // Send form data as JSON
            });
    
            if (response.ok) {
                console.log('Form submitted successfully');
                alert('Your message has been sent successfully!');
            } else {
                console.error('Failed to submit the form');
                alert('There was an error submitting your form. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('There was an error submitting your form. Please try again.');
        }
    
        // Reset form after submission
        setFormData({ name: '', email: '', message: '' });
    };
    

    return (
       <div id='contactcontain'>
         <div className="contact-container">
            <div className="contact-image" />
            <form className="contact-form" onSubmit={handleSubmit}>
                <h2>Contact Us</h2>
                <div className="form-group">
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="message">Message:</label>
                    <textarea
                        id="message"
                        name="message"
                        value={formData.message}
                        onChange={handleChange}
                        required
                    ></textarea>
                </div>
                <button type="submit">Send Message</button>
            </form>
        </div>
       </div>
    );
};

export default Contact;
