package br.ufrn.ePET.error;

public class ResourceNotFoundDetails extends ErrorDetails{

	public static final class Builder{
    	private String titulo;
    	
        private int status;
        
        private String detalhes;
        
        private long timestamp;
        
        private String mensagem;
        
        private Builder() { }
        
        public static Builder newBuilder() {
			return new Builder();
		}

		public Builder setTitulo(String titulo) {
			this.titulo = titulo;
			return this;
		}

		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}

		public Builder setDetalhes(String detalhes) {
			this.detalhes = detalhes;
			return this;
		}

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder setMensagem(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}

        public ResourceNotFoundDetails build(){
        	ResourceNotFoundDetails resourcenotfounddetails = new ResourceNotFoundDetails();
        	resourcenotfounddetails.setTitulo(titulo);
        	resourcenotfounddetails.setStatus(status);
        	resourcenotfounddetails.setDetalhes(detalhes);
        	resourcenotfounddetails.setTimestamp(timestamp);
        	resourcenotfounddetails.setMensagem(mensagem);
        	return resourcenotfounddetails;
        }
    	
    }
	
}
