import React, { useEffect, useState } from 'react';

const ViewMessage = () => {
    const [messages, setMessages] = useState([]);

    // Fetch messages from the backend API
    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await fetch('http://localhost:8080/admin/message');
                if (response.ok) {
                    const data = await response.json();
                    setMessages(data);
                } else {
                    console.error('Failed to fetch messages');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        };

        fetchMessages();
    }, []);

    return (
        <div id="viewcontactimage">
        <div className="view-messages-container">
            <h2 className="view-messages-heading">View Contact Messages</h2>
            {messages.length === 0 ? (
                <p className="no-messages">No messages found.</p>
            ) : (
                <div className="messages-table-container">
                    <table className="messages-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Message</th>
                            </tr>
                        </thead>
                        <tbody>
                            {messages.map((message) => (
                                <tr key={message.id}>
                                    <td>{message.name}</td>
                                    <td>{message.email}</td>
                                    <td>{message.message}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
        </div>
    );
};

export default ViewMessage;
