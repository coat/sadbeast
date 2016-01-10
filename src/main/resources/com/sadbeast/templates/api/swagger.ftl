{
  "swagger": "2.0",
  "info": {
    "description": "An API for a website that does nothing.",
    "version": "1.0.0",
    "title": "SadBeast API",
    "contact": {
      "email": "kentsmith@gmail.com"
    },
    "license": {
      "name": "Apache 2.0",
      "url": "http://www.apache.org/licenses/LICENSE-2.0.html"
    }
  },
  "host": "${host}",
  "basePath": "/api",
  "tags": [
    {
      "name": "content",
      "description": "Everything about posts"
    }
  ],
  "schemes": [
    "${scheme}"
  ],
  "paths": {
    "/content": {
      "get": {
        "tags": [
          "content"
        ],
        "summary": "Get a content",
        "description": "Currently only a random content is supported",
        "operationId": "randomPost",
        "produces": [
          "application/json"
        ],
        "parameters": [
          {
            "in": "query",
            "name": "random",
            "description": "If this parameter exists, you will get a random content",
            "required": true,
            "type": "string",
            "enum": [
              "random"
            ]
          }
        ],
        "responses": {
          "200": {
            "description": "successful operation",
            "schema": {
              "$ref": "#/definitions/Post"
            }
          }
        }
      }
    },
    "/posts": {
      "get": {
        "tags": [
          "content"
        ],
        "summary": "Get posts",
        "description": "Gets the latest 20 posts",
        "operationId": "getPosts",
        "produces": [
          "application/json"
        ],
        "parameters": [
          {
            "in": "query",
            "name": "prev",
            "description": "Get the previous 20 posts before the index specified in prev",
            "required": false,
            "type": "string"
          },
          {
            "in": "query",
            "name": "q",
            "description": "Search for posts containing the query in q, limited to 50 results",
            "required": false,
            "type": "string"
          }
        ],
        "responses": {
          "200": {
            "description": "successful operation",
            "schema": {
              "type": "array",
              "items": {
                "$ref": "#/definitions/Post"
              }
            }
          }
        }
      }
    }
  },
  "definitions": {
    "Post": {
      "required": [
        "content"
      ],
      "properties": {
        "content": {
          "type": "string",
          "example": "This is a content"
        },
        "created": {
          "type": "integer",
          "format": "date-time",
          "example": "2015-01-23T15:29:25.004+0000"
        }
      },
      "xml": {
        "name": "Post"
      }
    }
  }
}
