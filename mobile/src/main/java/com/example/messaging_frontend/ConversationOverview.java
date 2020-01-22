package com.example.messaging_frontend;

/**
 * Contains the meta data about a conversation.  Used as a cleaner way of sending Data
 * TODO: includes a contact instant and other relevant data, such as the latest message
 */
public class ConversationOverview {

//    private Contact myContact;
    private ConvMessage latestMessage;
    private String convID;


    public ConversationOverview(Builder builder)
    {
//        this.myContact = builder.myContact;
        this.latestMessage = builder.latestMessage;
        this.convID = builder.convID;
    }

    // builder
    public static class Builder {
//        private Contact myContact;
        private ConvMessage latestMessage;
        private String convID;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        // setters
        /*
        public Builder setMyContact(Contact myContact) {
            this.myContact = myContact;
            return this;
        }
         */

        public Builder setLatestMessage(ConvMessage latestMessage) {
            this.latestMessage = latestMessage;
            return this;
        }

        public Builder setConvID(String convID){
            this.convID = convID;
            return this;
        }

        public ConversationOverview build()
        {
            return new ConversationOverview(this);
        }


    }





    // getters
    /*
    public Contact getContact() {
        return myContact;
    }
     */

    public ConvMessage getLatestMessage() {
        return latestMessage;
    }

    public String getConvID() {
        return convID;
    }

    @Override
    public String toString()
    {
        return /*"Contact = " + this.myContact +*/ ", latest Message = " + this.latestMessage + ", convID = " + this.convID + ".";
    }
}
